//첫 번째 방법은 매우 무식하고 비효율적인 풀이입니다.
//실행시간이 1999밀리초로 거의 2초나 걸렸고, 이것은 하위 5%로 100명 중 약 95등에 해당하는 속도입니다.
//자바 언어에서 이미 제공하고 있는 기능들을 효율적으로 활용하기보다는, 그러한 기능들이 해주는 일들까지 전부 수작업으로 코딩해서 코드 길이가 길어지고 실행시간도 많이 걸렸습니다.
//대략적인 작동과정은 다음과 같습니다. 우선 주어진 입력에서 공백의 숫자를 세서 미리 저장함과 동시에 공백만으로 구성된 리스트를 하나 만들어 놓습니다.
//그 다음, 주어진 입력 배열을 그대로 복사합니다. 그리고 복사된 배열 내에 있는 모든 스트링 구성요소들을 전부 알파벳 순으로 바꿔줍니다.
//구성요소들이 전부 알파벳 순으로 바뀐 복사본 배열들 중에서 서로 다른 문자열들만 골라서 해시맵에 따로 저장합니다.
//해시맵에 저장된 요소들을 다시 스트링 배열로 바꾼 다음, 해당 스트링 배열의 요소별로 처음에 복사해 놓은 배열의 요소들과 비교합니다. 후자의 배열들을 구성하고 있는 각각의 스트링 요소들은 전부 알파벡 순으로 배열돼 있으므로, 해시맵의 키와 일치할 경우, 그 인덱스 그대로 원래 입력 배열의 인덱스에 있는 요소를 가져와서 스트링빌더에 집어넣어 줍니다.
//이렇게 만들어진 스트링 빌더 객체를 "."를 기준으로 나누어서 다시 문자열 배열로 만들고, 그 문자열 배열을 다시 리스트로 변환한 후 최종 반환용 리스트에 집어 넣습니다.
//최종 반환용 리스트에서 공백의 개수가 몇개이든 항상 1개로 나타나므로, 이러한 리스트 요소가 나타타는 인덱스를 찾아서 해당 인덱스 요소를 맨 처음에 만들어뒀던 공백 리스트로 대체합니다. 그리고 최종 답을 제출합니다.
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {

        int spaceNum = 0;
        List<String> spaceList = new ArrayList<>();
        for(int i=0; i<strs.length; i++){
            if(strs[i].equals("")){ spaceList.add(strs[i]); spaceNum++; }
        }

        String[] sorted = new String[strs.length];
        for(int i=0; i<strs.length; i++){
            char[] ca = strs[i].toCharArray().clone();
            Arrays.sort(ca);
            String sortedWord = String.valueOf(ca);
            sorted[i] = sortedWord;
        }

        HashMap<String, Integer> hm = new HashMap<>();
        for(int i=0; i<sorted.length; i++){
            if(!hm.containsKey(sorted[i])){
                hm.put(sorted[i],1);
            }
        }

        String[] keyWords = hm.keySet().toArray(new String[0]);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<keyWords.length; i++){
            for(int j=0; j<sorted.length; j++){
                if(keyWords[i].equals( sorted[j] ) && j!=sorted.length-1 ){
                    sb.append(strs[j]);
                    sb.append(",");
                }//if
                if(keyWords[i].equals( sorted[j] ) && j==sorted.length-1){
                    sb.append(strs[j]);
                }//if
            }//inner for
            if(i!=keyWords.length-1){
                sb.append(".");
            }
        }//outer for

        String[] bResult = sb.toString().split("\\.");
        List< List<String> > finalList = new ArrayList<>();
        for(int i=0; i<bResult.length; i++){
            String[] sas = bResult[i].split(",");
            List<String> list = new ArrayList<>();
            for(int j=0; j<sas.length; j++){
                list.add(sas[j]);
            }
            finalList.add(list);
        }

        if(spaceNum == 0){ return finalList; }
        else{
            int forChange = 0;
            for(int i = 0; i< finalList.size(); i++){
                if(finalList.get(i).isEmpty()){
                    forChange = i;
                }
            }
            finalList.set(forChange, spaceList);
            return finalList;
        }
    }//main
}//main class


//두 번째 풀이는 언어가 제공하는 기능들을 최대한 활용하여 빠른시간에 정확한 답을 도출하는 풀이들을 참고하여 제가 다시 풀어봤습니다.
//두 번재 풀이의 실행시간은 11밀리초에 상위 25%로, 100명중 약 25등 정도입니다.
//해시맵을 사용한 것은 첫 번째 풀이와 동일하지만, 첫 번째 풀이에서 해시맵의 '값' 부분을 사용하지 않은 것에 비해, 두 번째 풀이에서는 해시맵의 '값'부분에 아예 새로운 스트링 리스트를 집어넣어서 적극 활용하고 있습니다.
//최초의 입력 배열에서 스트링 요소가 순차적으로 for-each 반복문으로 전달되는데, 이때 전달된 스트링을 알파벳 순으로 정렬해서 해시맵의 키로 설정하고, 그 키에 짝지어질 '값'부분에서 스트링 리스트 인스턴스를 만들어줍니다. 매번 최초 입력 배열에서 스트링이 올 때마다 알파벳 순으로 정렬하여 일치하는 키가 있으면 해당 키의 값에 해당하는 리스트에 집어넣고, 없으면 새로운 키로 설정하고 그에 해당하는 스트링 리스트 인스턴스를 만들어줍니다.
//이것은 자바 언어의 해시맵 클래스가 제공하는 기본 기능을 최대한 활용하고 있으며, 첫 번재 풀이와 같은 복잡한 논리 및 경우의 수 처리가 필요 없습니다.
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0 || strs == null) return new ArrayList<>();

        Map<String, List<String>> map = new HashMap<>();

        for (String elem : strs) {
            char[] carr = elem.toCharArray();
            Arrays.sort(carr);
            String keyStr = String.valueOf(carr);
            if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<>());
            map.get(keyStr).add(elem);
        }

        return new ArrayList<>(map.values());

    }// main
}//main class
