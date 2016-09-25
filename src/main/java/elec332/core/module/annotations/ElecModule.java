package elec332.core.module.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Elec332 on 9-4-2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ElecModule {

    /**
     * The mod ID of the mod that "owns" this module.
     *
     * @return The modId
     */
    String owner();

    /**
     * @return The name of this module
     */
    String name();

    /**
     * Returns an array of modId's this module depends on
     * (owner mod does not count).
     * This gets parsed the same way as in the
     * {@link net.minecraftforge.fml.common.Mod} annotation.
     *
     * @return The mod dependencies
     */
    String modDependencies() default "";

    /**
     * Returns an array of module names this module depends on.
     * Format: modId:moduleName
     *
     * @return The mod dependencies
     */
    String moduleDependencies() default "";

    boolean enabled() default true;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface EventHandler {}

}


