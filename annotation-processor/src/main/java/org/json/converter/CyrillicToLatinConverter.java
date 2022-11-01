package org.json.converter;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class CyrillicToLatinConverter {

    private final static Map<String, String> CYRILLIC_TO_LATIN_MAP;

    static {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("\u0410", "А");
        builder.put("\u0430", "а");
        builder.put("\u0411", "B");
        builder.put("\u0431", "b");
        builder.put("\u0412", "V");
        builder.put("\u0432", "v");
        builder.put("\u0413", "G");
        builder.put("\u0433", "g");
        builder.put("\u0414", "D");
        builder.put("\u0434", "d");
        builder.put("\u0402", "\u0110");
        builder.put("\u0452", "\u0111");
        builder.put("\u0415", "E");
        builder.put("\u0435", "e");
        builder.put("\u0416", "\u017D");
        builder.put("\u0436", "\u017E");
        builder.put("\u0417", "Z");
        builder.put("\u0437", "z");
        builder.put("\u0418", "I");
        builder.put("\u0438", "i");
        builder.put("\u0408", "J");
        builder.put("\u0458", "j");
        builder.put("\u041A", "K");
        builder.put("\u043A", "k");
        builder.put("\u041B", "L");
        builder.put("\u043B", "l");
        builder.put("\u0409", "Lj");
        builder.put("\u0459", "lj");
        builder.put("\u041C", "M");
        builder.put("\u043C", "m");
        builder.put("\u041D", "N");
        builder.put("\u043D", "n");
        builder.put("\u040A", "Nj");
        builder.put("\u045A", "nj");
        builder.put("\u041E", "O");
        builder.put("\u043E", "o");
        builder.put("\u041F", "P");
        builder.put("\u043F", "p");
        builder.put("\u0420", "R");
        builder.put("\u0440", "r");
        builder.put("\u0421", "S");
        builder.put("\u0441", "s");
        builder.put("\u0422", "T");
        builder.put("\u0442", "t");
        builder.put("\u040B", "\u0106");
        builder.put("\u045B", "\u0107");
        builder.put("\u0423", "U");
        builder.put("\u0443", "u");
        builder.put("\u0424", "F");
        builder.put("\u0444", "f");
        builder.put("\u0425", "H");
        builder.put("\u0445", "h");
        builder.put("\u0426", "C");
        builder.put("\u0446", "c");
        builder.put("\u0427", "\u010C");
        builder.put("\u0447", "\u010D");
        builder.put("\u040F", "D\u017E");
        builder.put("\u045F", "d\u017E");
        builder.put("\u0428", "\u0160");
        builder.put("\u0448", "\u0161");
        CYRILLIC_TO_LATIN_MAP = builder.build();
    }

    public static String convertCharToLatin(Character character) {
        return CYRILLIC_TO_LATIN_MAP.getOrDefault(character.toString(), character.toString());
    }
}
