package ru.java.digdes.task;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    /**
     * Метод распаковки строки
     *
     * @param in String Строка для распаковки
     * @return String развернутая строка
     */
    public static String unpack(String in) {
        String regex = "(\\d+\\[[a-z]+])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(in);
        if(matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String substring = in.substring(start, end);//Искомая подстрока
            int firstIndexBracket = substring.indexOf("[");
            int lastIndexBracket = substring.indexOf("]");
            String letters = substring.substring(firstIndexBracket + 1, lastIndexBracket);//Буквы подстроки
            int digitRepeat = Integer.parseInt(substring.substring(0, firstIndexBracket));//Число повторений
            String replace = letters.repeat(digitRepeat);//Измененная подстрока
            in = matcher.replaceFirst(replace);//Измененная Строка
        }
        return in;
    }

    /**
     * Проверка строки на правильное расположение всех допустимых символов для корректной распаковки.
     *
     * @param in String Проверяемая строка
     * @return true при успехе, иначе false
     */
    public static boolean valid(String in) {
        if(hasValidChar(in) && hasEqualBrackets(in)) {
            boolean isValid = true;
            char[] chars = in.toCharArray();
            for(int i = 0; i < chars.length; i++) {
                if(chars[0] == '[') {
                    return false;
                }
                if(i == chars.length - 1) {
                    return isValid;
                }
                if(Character.isDigit(chars[i])) {
                    String nextChar = String.valueOf(chars[i + 1]);
                    if(!nextChar.equals("[") && !nextChar.matches("[0-9]")) {
                        isValid = false;
                    }
                } else if(Character.isAlphabetic(chars[i])) {
                    String nextChar = String.valueOf(chars[i + 1]);
                    if(nextChar.matches("\\[")) {
                        isValid = false;
                    }
                } else if(String.valueOf(chars[i]).equals("[")) {
                    String nextChar = String.valueOf(chars[i + 1]);
                    if(nextChar.matches("\\[") || nextChar.matches("]")) {
                        isValid = false;
                    }
                } else if(String.valueOf(chars[i]).equals("]")) {
                    String nextChar = String.valueOf(chars[i + 1]);
                    if(nextChar.matches("\\[") || nextChar.matches("]")) {
                        isValid = false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Проверка строки на содержание только допустимых символов(буквы латинского алфавита, цифры и квадратные скобки)
     *
     * @param in String Проверяемая строка
     * @return true при успехе, иначе false
     */
    public static boolean hasValidChar(String in) {
        String validChar = "^[a-zA-Z0-9\\[\\]]+";
        return in.matches(validChar);
    }

    /**
     * Проверка строки на содержание одинакового количества открывающих и закрывающих скобок.
     *
     * @param in String Проверяемая строка
     * @return true при успехе, иначе false
     */
    public static boolean hasEqualBrackets(String in) {
        int countFirstBracket = 0, countLastBracket = 0;
        for(char element : in.toCharArray()) {
            if(element == '[') countFirstBracket++;
            if(element == ']') countLastBracket++;
        }
        return countFirstBracket == countLastBracket;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Введите строку для распаковки:");
            String in = scanner.nextLine();
            if(valid(in)) {
                while(in.contains("[")) {
                    in = unpack(in);
                }
                System.out.println("Результат: " + in);
                break;
            } else {
                System.out.println("Строка не валидна. Попробуйте еще раз.");
            }
        }
    }
}
