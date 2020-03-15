package org.preventime.config;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import org.preventime.data.util.AbstractEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.metamodel.Type;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Configuration
public class DataRestConfiguration implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    public DataRestConfiguration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(entityClasses());
    }

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new Module() {
            @Override
            public String getModuleName() {
                return "org.preventime";
            }

            @Override
            public Version version() {
                return Version.unknownVersion();
            }

            @Override
            public void setupModule(Module.SetupContext context) {

                context.addBeanSerializerModifier(new BeanSerializerModifier() {
                    @Override
                    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
                        if (AbstractEntity.class.isAssignableFrom(beanDesc.getBeanClass())) {
                            return new UnwrappingEntitySerializer((BeanSerializerBase) serializer, NameTransformer.NOP);
                        }
                        return serializer;
                    }
                });
            }
        });
    }

    public static class UnwrappingEntitySerializer extends UnwrappingBeanSerializer {
        public UnwrappingEntitySerializer(BeanSerializerBase src, NameTransformer transformer) {
            super(src, transformer);
        }

        @Override
        public JsonSerializer<Object> unwrappingSerializer(NameTransformer transformer) {
            return new UnwrappingEntitySerializer(this, transformer);
        }

        @Override
        protected void serializeFields(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
            List<Field> fields = getAllFields(new LinkedList<>(), bean.getClass());
            super.serializeFields(bean, jgen, provider);
            for (Field field : fields) {
                if (field.getAnnotation(ManyToOne.class) != null) {
                    Object value = null;
                    try {
                        field.setAccessible(true);
                        value = field.get(bean);
                    } catch (IllegalAccessException e) {
                        throw new JsonGenerationException(e);
                    }
                    if (value == null) {
                        jgen.writeNullField(field.getName());
                    } else {
                        if (value instanceof AbstractEntity) {
                            jgen.writeStringField(field.getName(), ((AbstractEntity) value).getId());
                        }
                    }
                }
                if (field.getAnnotation(ManyToMany.class) != null) {
                    Object values = null;
                    try {
                        field.setAccessible(true);
                        values = field.get(bean);
                    } catch (IllegalAccessException e) {
                        throw new JsonGenerationException(e);
                    }
                    if (values == null) {
                        jgen.writeNullField(field.getName());
                    } else {
                        if (values instanceof Collection) {
                            List<String> ids = new ArrayList<>();
                            for (Object value : (Collection) values) {
                                if (value instanceof AbstractEntity) {
                                    ids.add(((AbstractEntity) value).getId());
                                }
                            }
                            jgen.writeFieldName(field.getName());
                            jgen.writeStartArray(ids.size());
                            for (String id : ids) {
                                jgen.writeString(id);
                            }
                            jgen.writeEndArray();
                        }
                    }
                }
            }
        }

        @Override
        public boolean isUnwrappingSerializer() {
            return true;
        }
        private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
            fields.addAll(Arrays.asList(type.getDeclaredFields()));

            if (type.getSuperclass() != null) {
                getAllFields(fields, type.getSuperclass());
            }

            return fields;
        }

    }

    private Class<?>[] entityClasses() {
        return entityManager.getMetamodel()
                .getEntities()
                .stream()
                .map(Type::getJavaType)
                .toArray(Class[]::new);
    }
}
