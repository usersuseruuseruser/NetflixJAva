package com.example.enums;

public enum ContentTypes {
    MOVIE {
        @Override
        public String toString() {
            return "Фильм";
        }
    },
}
