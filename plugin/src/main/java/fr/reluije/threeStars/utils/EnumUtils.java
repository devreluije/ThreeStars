package fr.reluije.threeStars.utils;

public class EnumUtils {

    public static <E extends Enum<E>> E get(Class<E> enumType, String type, E defaultValue) {
        try {
            return Enum.valueOf(enumType, type);
        } catch (Throwable e) {
            return defaultValue;
        }
    }
}
