package fr97.umlfx.common;

public enum AccessModifier {

    PRIVATE{
        @Override
        String getUmlSign() {
            return "-";
        }
    },
    PROTECTED{
        @Override
        String getUmlSign() {
            return "#";
        }
    },
    PUBLIC{
        @Override
        String getUmlSign() {
            return "+";
        }
    },
    DEFAULT{
        @Override
        String getUmlSign() {
            return "";
        }
    };

    abstract String getUmlSign();

}
