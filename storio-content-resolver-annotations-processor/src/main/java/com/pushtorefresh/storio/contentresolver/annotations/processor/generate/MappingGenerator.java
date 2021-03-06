package com.pushtorefresh.storio.contentresolver.annotations.processor.generate;

import com.pushtorefresh.storio.common.annotations.processor.generate.Generator;
import com.pushtorefresh.storio.contentresolver.annotations.processor.introspection.StorIOContentResolverTypeMeta;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.jetbrains.annotations.NotNull;

import static com.pushtorefresh.storio.common.annotations.processor.generate.Common.INDENT;
import static javax.lang.model.element.Modifier.PUBLIC;

public class MappingGenerator implements Generator<StorIOContentResolverTypeMeta> {

    public static final String SUFFIX = "ContentResolverTypeMapping";

    @NotNull
    @Override
    public JavaFile generateJavaFile(@NotNull StorIOContentResolverTypeMeta storIOSQLiteTypeMeta) {
        final ClassName storIOSQLiteTypeClassName = ClassName.get(
                storIOSQLiteTypeMeta.packageName, storIOSQLiteTypeMeta.simpleName);

        ClassName superclass = ClassName.get("com.pushtorefresh.storio.contentresolver", SUFFIX);
        ParameterizedTypeName superclassParametrized =
                ParameterizedTypeName.get(superclass, storIOSQLiteTypeClassName);


        final TypeSpec mapping = TypeSpec.classBuilder(storIOSQLiteTypeMeta.simpleName + SUFFIX)
                .addJavadoc("Generated mapping with collection of resolvers\n")
                .addModifiers(PUBLIC)
                .superclass(superclassParametrized)
                .addMethod(createConstructor(storIOSQLiteTypeMeta))
                .build();

        return JavaFile
                .builder(storIOSQLiteTypeMeta.packageName, mapping)
                .indent(INDENT)
                .build();
    }

    @NotNull
    private MethodSpec createConstructor(StorIOContentResolverTypeMeta storIOSQLiteTypeMeta) {
        final ClassName putResolver = ClassName.get(storIOSQLiteTypeMeta.packageName,
                PutResolverGenerator.generateName(storIOSQLiteTypeMeta));
        final ClassName getResolver = ClassName.get(storIOSQLiteTypeMeta.packageName,
                GetResolverGenerator.generateName(storIOSQLiteTypeMeta));
        final ClassName deleteResolver = ClassName.get(storIOSQLiteTypeMeta.packageName,
                DeleteResolverGenerator.generateName(storIOSQLiteTypeMeta));

        return MethodSpec.constructorBuilder()
                .addModifiers(PUBLIC)
                .addStatement("super(new $T(),\nnew $T(),\nnew $T())",
                        putResolver, getResolver, deleteResolver)
                .build();
    }
}
