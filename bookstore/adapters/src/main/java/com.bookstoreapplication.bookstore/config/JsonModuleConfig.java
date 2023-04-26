package com.bookstoreapplication.bookstore.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import dev.mccue.json.*;
import dev.mccue.json.stream.JsonWriteable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.UncheckedIOException;

@Configuration
class JsonModuleConfig {
    @Bean
    public Module jsonModuleSerializer() {
        var module = new SimpleModule();
        module.addSerializer(new StdSerializer<>(JsonWriteable.class) {
            @Override
            public void serialize(
                    JsonWriteable writeable,
                    JsonGenerator jsonGenerator,
                    SerializerProvider serializerProvider
            ) throws IOException {
                try {
                    writeable.write(new ProxyWriter(jsonGenerator));
                } catch (UncheckedIOException e) {
                    throw e.getCause();
                }
            }
        });
        module.addDeserializer(Json.class, new StdDeserializer<>(Json.class) {
            private Json deserializeTree(JsonNode tree) {
                if (tree.isTextual()) {
                    return JsonString.of(tree.textValue());
                }
                else if (tree.isNull()) {
                    return JsonNull.instance();
                }
                else if (tree.isBoolean()) {
                    return JsonBoolean.of(tree.booleanValue());
                }
                else if (tree.isInt()) {
                    return JsonNumber.of(tree.intValue());
                }
                else if (tree.isLong()) {
                    return JsonNumber.of(tree.longValue());
                }
                else if (tree.isDouble()) {
                    return JsonNumber.of(tree.doubleValue());
                }
                else if (tree.isBigDecimal()) {
                    return JsonNumber.of(tree.decimalValue());
                }
                else if (tree.isBigInteger()) {
                    return JsonNumber.of(tree.bigIntegerValue());
                }
                else if (tree.isArray()) {
                    var arrayBuilder = JsonArray.builder();
                    for (var value : tree) {
                        arrayBuilder.add(deserializeTree(value));
                    }
                    return arrayBuilder.build();
                }
                else if (tree.isObject()) {
                    var objectBuilder = JsonObject.builder();
                    var fieldNamesIter = tree.fieldNames();
                    while (fieldNamesIter.hasNext()) {
                        var fieldName = fieldNamesIter.next();
                        objectBuilder.put(fieldName, deserializeTree(tree.get(fieldName)));
                    }
                    return objectBuilder.build();
                }
                else {
                    throw new IllegalStateException("Should have handled all JsonNode types?");
                }
            }

            @Override
            public Json deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException {
                return deserializeTree(jsonParser.readValueAsTree());
            }
        });
        return module;
    }

    private record ProxyWriter(JsonGenerator jsonGenerator)
            implements dev.mccue.json.stream.JsonGenerator {
        @Override
        public void writeObjectStart() {
            try {
                jsonGenerator.writeStartObject();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public void writeObjectEnd() {
            try {
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public void writeArrayStart() {
            try {
                jsonGenerator.writeStartArray();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public void writeArrayEnd() {
            try {
                jsonGenerator.writeEndArray();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public void writeFieldName(String s) {
            try {
                jsonGenerator.writeFieldName(s);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public void writeString(String s) {
            try {
                jsonGenerator.writeString(s);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public void writeNumber(JsonNumber jsonNumber) {
            try {
                jsonGenerator.writeNumber(jsonNumber.toString());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public void writeTrue() {
            try {
                jsonGenerator.writeBoolean(true);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public void writeFalse() {
            try {
                jsonGenerator.writeBoolean(false);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public void writeNull() {
            try {
                jsonGenerator.writeNull();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}