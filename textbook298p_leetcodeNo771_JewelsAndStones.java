//첫 번째 풀이는 해시맵을 이용한 풀이입니다. 실행시간은 3밀리초였고, 이는 하위 30%의 성능입니다.
class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        //return type is int
        
        if(jewels.length()==0) return 0;
        
        HashMap<Character, Integer> hm = new HashMap<>();
        
        char[] jList = jewels.toCharArray();
        
        for(char c : jList){
            hm.put(c,0);
        }
        
        char[] sList = stones.toCharArray();
        
        for(char c : sList){
            if(hm.containsKey(c)){
                int value = hm.get(c);
                value++;
                hm.put(c,value);
            }
        }
        
        int num = 0;
        for(int j : hm.values()){
            num += j;
        }
        
        return num;
        
    }//main
}//main class




//사실 이 문제는 굳이 해시맵을 동원하지 않고도 해결할 수 있습니다.
//스트링의 인덱스를 직접 검색하는 방식으로 풀이하면 실행시간을 1밀리초 단축할 수 있고, 이는 상위 50%의 성능입니다.
class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        
        int count = 0;
       for(int i = 0; i < stones.length(); i++){
           if(jewels.indexOf(stones.charAt(i)) != -1)
               count++;
       }
        return count;
    }
}
