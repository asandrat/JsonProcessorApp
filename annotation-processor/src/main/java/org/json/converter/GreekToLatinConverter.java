package org.json.converter;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class GreekToLatinConverter {

    private final static Map<String, String> GREEK_TO_LATIN_MAP;

    static {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("Α", "A");
        builder.put("Ά", "A");
        builder.put("α", "a");
        builder.put("ά", "a");
        builder.put("Β", "V");
        builder.put("β", "v");
        builder.put("Γ", "G");
        builder.put("γ", "g");
        builder.put("Δ", "D");
        builder.put("δ", "d");
        builder.put("Ε", "E");
        builder.put("Έ", "E");
        builder.put("ε", "e");
        builder.put("έ", "e");
        builder.put("Ζ", "Z");
        builder.put("ζ", "z");
        builder.put("Η", "I");
        builder.put("Ή", "I");
        builder.put("η", "i");
        builder.put("ή", "i");
        builder.put("Θ", "Th");
        builder.put("θ", "th");
        builder.put("Ι", "I");
        builder.put("Ί", "I");
        builder.put("Ϊ", "I");
        builder.put("ι", "i");
        builder.put("ί", "i");
        builder.put("ϊ", "i");
        builder.put("ΐ", "i");
        builder.put("Κ", "K");
        builder.put("κ", "k");
        builder.put("Λ", "L");
        builder.put("λ", "l");
        builder.put("Μ", "M");
        builder.put("μ", "m");
        builder.put("Ν", "N");
        builder.put("ν", "n");
        builder.put("Ξ", "X");
        builder.put("ξ", "x");
        builder.put("Ο", "O");
        builder.put("Ό", "O");
        builder.put("ο", "o");
        builder.put("ό", "o");
        builder.put("Π", "P");
        builder.put("π", "p");
        builder.put("Ρ", "R");
        builder.put("ρ", "r");
        builder.put("Σ", "S");
        builder.put("σ", "s");
        builder.put("ς", "s");
        builder.put("Τ", "T");
        builder.put("τ", "t");
        builder.put("Υ", "Y");
        builder.put("Ύ", "Y");
        builder.put("Ϋ", "Y");
        builder.put("υ", "y");
        builder.put("ύ", "y");
        builder.put("ϋ", "y");
        builder.put("ΰ", "y");
        builder.put("Φ", "F");
        builder.put("φ", "f");
        builder.put("Χ", "Ch");
        builder.put("χ", "ch");
        builder.put("Ψ", "Ps");
        builder.put("ψ", "ps");
        builder.put("Ω", "O");
        builder.put("Ώ", "O");
        builder.put("ω", "o");
        builder.put("ώ", "o");
        GREEK_TO_LATIN_MAP = builder.build();
    }

    public static String convertStringToLatin(String string) {
        return GREEK_TO_LATIN_MAP.getOrDefault(string, string);
    }
}