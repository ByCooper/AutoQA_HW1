package inno.tech;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

    }

    //Задача №1
    public static boolean isEven(int n){
        return n % 2 == 0;
    }

    //Задача №2
    public static String checkAccess(int age){
        return (age > 18) ? "Allowed" : "Denied";
    }

    //Задача №3
    public static boolean isPositive(int n){
        return n >= 0;
    }

    //Задача №4
    public static String getGrade(int score){
        if(score >= 0 && score <= 20){
            return "E";
        }
        else if(score >= 21 && score <= 40){
            return "D";
        }
        else if(score >= 41 && score <= 60){
            return "C";
        }
        else if(score >= 61 && score <= 80){
            return "B";
        }
        else if(score >= 81 && score <= 100){
            return "A";
        }
        else return "Error";
    }

    //Задача №5
    public static String blastOff(int start) {
        StringBuilder result = new StringBuilder(start + "");
        for(int i = start - 1; i >= 1 ; i--) {
            result.append(", ").append(i);
        }
        result.append(". Поехали!");
        return result.toString();
    }

    //Задача №6
    public static int sumToN(int n){
        int result = n;
        for (int i = 1; i < n; i++) {
            result += i;
        }
        return result;
    }

    //Задача №7
    public static boolean hasBug(String[] messages) {
        return Arrays.stream(messages)
                .anyMatch(e -> e != null && e.equalsIgnoreCase("Bug"));
    }

    //Задача №8
    public static String getEvenInRange(int start, int end) {
        StringBuilder result = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if(i == end || i == end - 1) {
                result.append(i);
                break;
            } else if(i % 2 == 0) {
                result.append(i).append(" ");
            }
        }
        return result.toString();
    }

    //Задача №9
    public static int findMax(int[] arr) {
        int resultMaxDigit = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if(arr[i] > resultMaxDigit) {
                resultMaxDigit = arr[i];
            }
        }
        return resultMaxDigit;
    }

    //Задача №10
    public static String[] revers(String[] arr) {
        int begin = 0;
        int end = arr.length - 1;
        while (begin < end) {
            String buffer = arr[begin];
            arr[begin] = arr[end];
            arr[end] = buffer;
            begin++;
            end--;
        }
        return arr;
    }

    //Задача №11
    public static Double calcAverage(List<Integer> list) {
        return list.stream()
                .collect(Collectors.averagingInt(Integer::intValue));
    }

    //Задача №12
    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        List<String> result = list.stream()
                .filter(e -> !e.equalsIgnoreCase(nameToRemove))
                .collect(Collectors.toList());
        return result;
    }
}