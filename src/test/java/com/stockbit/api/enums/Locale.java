package com.stockbit.api.enums;

public enum Locale {

    ENGLISH_US("en_US", "English (US)"),
    ENGLISH_GB("en_GB", "English (GB)"),
    INDONESIAN("id_ID", "Indonesian"),
    FRENCH_FR("fr_FR", "French (FR)"),
    GERMAN_DE("de_DE", "German (DE)"),
    SPANISH_ES("es_ES", "Spanish (ES)"),
    ITALIAN_IT("it_IT", "Italian (IT)"),
    PORTUGUESE_PT("pt_PT", "Portuguese (PT)"),
    DUTCH_NL("nl_NL", "Dutch (NL)"),
    POLISH_PL("pl_PL", "Polish"),
    RUSSIAN_RU("ru_RU", "Russian"),
    JAPANESE_JA("ja_JP", "Japanese"),
    CHINESE_ZH("zh_CN", "Chinese"),
    KOREAN_KO("ko_KR", "Korean"),
    ARABIC_AR("ar_AE", "Arabic"),
    HINDI_HI("hi_IN", "Hindi"),
    TURKISH_TR("tr_TR", "Turkish"),
    VIETNAMESE_VI("vi_VN", "Vietnamese"),
    THAI_TH("th_TH", "Thai"),
    SWEDISH_SV("sv_SE", "Swedish"),
    NORWEGIAN_NO("no_NO", "Norwegian"),
    DANISH_DA("da_DK", "Danish"),
    FINNISH_FI("fi_FI", "Finnish");

    private final String code;
    private final String name;

    Locale(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return code;
    }

    public static Locale fromCode(String code) {
        for (Locale locale : values()) {
            if (locale.code.equalsIgnoreCase(code)) {
                return locale;
            }
        }
        throw new IllegalArgumentException("Unknown locale code: " + code);
    }
}
