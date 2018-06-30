package fr97.umlfx.common;

public enum Stereotype {
    NO_STEREOTYPE {
        @Override
        public String getText() {
            return "";
        }
    },
    INTERFACE{
        @Override
        public String getText() {
            return "<<interface>>";
        }
    },
    ENUM{
        @Override
        public String getText() {
            return "<<enumeration>>";
        }
    };


    public abstract String getText();
}
