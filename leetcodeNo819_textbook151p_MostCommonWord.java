//첫 번째 풀이는 ArrayList와 커스텀 클래스인 Word를 이용한 풀이입니다.
//이미 자바 언어에서 제공하고 있는 좋은 클래스들 중 하나인 HashSet, HashMap 등을 활용하지 않아서 코드 길이가 길어졌습니다. 또한 자바에서 이미 제공하고 있는 문자열 관련 유용한 메서스들을 거의 활용하지 않아서 논리적으로 복잡한 코드가 되었습니다.
//논리적으로 신경써야 할 부분이 많아서 지칫 잘못하면 디버깅하는 것에 시간을 크게 낭비해 버릴 수 있는 코드입니다.
//실행시간은 10밀리초였고, 상위 약 10%으며 100명 중 10등 정도입니다.
class Solution {
    public String mostCommonWord(String paragraph, String[] banned) {

        String lower = paragraph.toLowerCase();
        char[] lowers = lower.toCharArray();

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<lowers.length; i++){
            if( Character.isLetterOrDigit( lowers[i] ) ){
                sb.append( lowers[i] );
            }
            else{
                sb.append(" ");
            }
        }//for

        String filtered = sb.toString();

        String[] fResult = filtered.split(" ");

        ArrayList<Word> list = new ArrayList<>();

        for(int i=0; i<fResult.length; i++){
            int num = 0;

            if(i==0){
                Word w = new Word(fResult[i], 1);
                list.add(w);
                continue;
            }

            for(int j=0; j<list.size(); j++){
                //일치 하는 않는 경우 num에 +=1 한다.
                if( !fResult[i].equals( list.get(j).name ) ){
                    num ++;
                }
                else/*일치하는 경우*/{
                    list.get(j).value ++;
                }
            }//inner for

            if(num == list.size()){
                list.add( new Word(fResult[i],1) );
            }

        }//outer for

        //밴 당한 것 거르기 여기서 공백도 걸러야 한다.
        for(int i=0; i<banned.length; i++){
            for(int j=0; j<list.size(); j++) {
                if (banned[i].equals(list.get(j).name)) {
                    list.get(j).value = -1;
                }
                if(list.get(j).name.equals("")){
                    list.get(j).value = -1;
                }
            }
        }//for

        //최댓값 찾기
        int greatest = 0;
        String answer = "";
        for(int i=0; i<list.size(); i++){
            if( greatest <= list.get(i).value ){
                greatest = list.get(i).value;
                answer = list.get(i).name;
            }
        }

        return answer;

    }//main

    //단어, 그리고 그 단어가 등장한 빈도. 이렇게 2 가지 요소를 갖고 있는 커스텀 클래스.
    class Word{
        public String name;
        public int value = 0;
        public Word(String n, int k){
            name = n;
            value = k;
        }
    }

}//main class





//자바에서 제공하는 유용한 클래스와 메서스들을 최대한 활용 돼 있는 두 번째 풀이를 리트코드에서 가져왔습니다..
//대표적인 예로 for-each 루프, HashSet과 HashMap을 들 수 있습니다.
//그리고 정규표현식을 사용하여 코드의 길이를 대폭 줄이고, HashMap과 HashSet에서 제공하는 메서드를 적절히 활용하여 간결한 코드로 깔끔하게 정답을 도출하고 있었습니다. 본받을 만한 코드라고 생각합니다.
class Solution {
    public String mostCommonWord(String paragraph, String[] banned) {
        HashSet<String> bannedWords = new HashSet<String>();
        
        for(String word : banned){
            bannedWords.add(word);
        }
        
        HashMap<String,Integer> wordsCount = new HashMap<String,Integer>();
        
        for(String word : paragraph.replaceAll("[^a-zA-Z]"," ").toLowerCase().split(" ")){
            if(!bannedWords.contains(word))
                wordsCount.put(word, wordsCount.getOrDefault(word,0)+1);
        }
        
        String result = "";
        
        for(String key : wordsCount.keySet()){
            if(result == "" || wordsCount.get(key) > wordsCount.get(result))
                result = key;
        }
        
        return result;
    }//main
}//main class
