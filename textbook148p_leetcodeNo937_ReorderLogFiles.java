//첫 번째 풀이입니다. 로그 스트링을 저장하는 새로운 타입을 정의해서 풀었습니다.
//코드가 길고 복잡합니다.
//실행시간은 18밀리초, 상위 82%로 100명 중 82등 정도입니다.
public class Solution {
    public String[] reorderLogFiles(String[] logs) {
        List<Log> digits = new ArrayList<>();
        List<Log> letters = new ArrayList<>();
        for (String str : logs) {
            Log log = new Log(str);
            if (log.isDigit()) {
                digits.add(log);
            } else {
                letters.add(log);
            }
        }

        letters.sort((o1, o2) -> {
            int compare = o1.getValues().compareTo(o2.getValues());
            if (compare == 0) {
                return o1.getIdentifier().compareTo(o2.getIdentifier());
            }
            return compare;
        });

        List<String> sorted = new ArrayList<>();
        for (Log log : letters) {
            sorted.add(log.toString());
        }
        for (Log log : digits) {
            sorted.add(log.toString());
        }

        return sorted.toArray(new String[sorted.size()]);
    }

    class Log {
        private static final String DELIMITER = " ";
        String[] values;

        public Log(String log) {
            this.values = log.split(DELIMITER);
        }

        public boolean isDigit() {
            for (int i = 1, n = values.length; i < n; i++) {
                char[] chars = values[i].toCharArray();
                for (char c : chars) {
                    if (!Character.isDigit(c)) {
                        return false;
                    }
                }
            }

            return true;
        }

        public String getIdentifier() {
            return values[0];
        }

        public String getValues() {
            StringBuilder joiner = new StringBuilder();
            for (int i = 1; i < values.length; i++) {
                if(i!=(values.length-1)){
                    joiner.append(values[i]);
                    joiner.append(" ");
                }
                else{
                    joiner.append(values[i]);
                }
            }

            return joiner.toString();
        }

        public String toString() {
            StringBuilder joiner = new StringBuilder();
            for (int i=0; i<values.length; i++) {
                if(i!=(values.length-1)){
                    joiner.append(values[i]);
                    joiner.append(" ");
                }
                else{
                    joiner.append(values[i]);
                }
            }

            return joiner.toString();
        }
    }

}


//두 번째 풀이는 별도의 로그 파일을 만들지 않고 스트링 자체에서 제공하는 메서드들을 최대한 활용하되, 커스텀 비교 객체인 MySort를 만들어서 해결했습니다.
//실행시간은 5밀리초에, 상위 15%로 100명중 약 15등 정도입니다.
class Solution {
    public String[] reorderLogFiles(String[] logs) {
        List<String> letters = new ArrayList<>();
        List<String> digits = new ArrayList<>();
        for(String s: logs) {
            int i = s.indexOf(" ");
            char ch = s.charAt(i+1);
            if('0' <= ch && ch <= '9' ) {
                digits.add(s);
            }
            else {
                letters.add(s);
            }
        }
    
        
        Collections.sort(letters, new MySort());
        
        String[] result = new String[letters.size() + digits.size()];
        int i = 0;
        for(String s: letters) {
            result[i] = s;
            i++;
        }
        for(String s: digits) {
            result[i] = s;
            i++;
        }
        return result;            
    }//main
    
    class MySort implements Comparator<String> {
 
    
        public int compare(String s1, String s2)
        {
                int index1 = s1.indexOf(" ");
                String id1 = s1.substring( 0, index1 );
                String letter1 = s1.substring( index1+1 );
            
                int index2 = s2.indexOf(" ");
                String id2 = s2.substring( 0, index2 );
                String letter2 = s2.substring( index2+1 );
                int v1 = letter1.compareTo(letter2);
                if(v1 != 0) return v1;
                int v2 = id1.compareTo(id2);
                return v2;
        }
    }
}//end of Solution
