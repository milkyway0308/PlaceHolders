package skywolf46.placeholders.util;

// 소스 코드 원본:
// https://okky.kr/article/549842
public class PostWordDetector {
    public static String getPostWord(String str, String firstVal, String secondVal, String defValue) {
        try {
            char laststr = str.charAt(str.length() - 1);
            // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
            if (laststr < 0xAC00 || laststr > 0xD7A3) {
                return defValue;
            }
            int lastCharIndex = (laststr - 0xAC00) % 28;
            // 종성인덱스가 0이상일 경우는 받침이 있는경우이며 그렇지 않은경우는 받침이 없는 경우
            if(lastCharIndex > 0) {
                // 받침이 있는경우
                // 조사가 '로'인경우 'ㄹ'받침으로 끝나는 경우는 '로' 나머지 경우는 '으로'
                if(firstVal.equals("으로") && lastCharIndex == 8) {
                    return secondVal;
                } else {
                    return firstVal;
                }
            } else {
                // 받침이 없는 경우
                return secondVal;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return defValue;
    }
}
