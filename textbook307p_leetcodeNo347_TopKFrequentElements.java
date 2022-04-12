//첫 번째 풀이는 실행시간이 무려 111밀리초나 걸렸고, 하위 5%의 성적을 기록했습니다.
//해시맵을 만드는 것 까지는 다른 풀이들과 비슷한데, 그 중에서 상위 k개의 요소를 걸러내는 부분에서 PriorityQueue 자료형을 사용하는 방법을 몰라서, 할 수 없이 해시맵에서 가장 값이 큰 키를 골라낸 후 삭제하는 별도의 함수를 작성해서 해결했습니다.
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        //return type is int[].
        
        HashMap<Integer, Integer> hm = new HashMap<>();
        
        for(int i : nums){
            if(!hm.containsKey(i)){
                hm.put(i,1);
            }
            else{
                int p = hm.get(i);
                p++;
                hm.put(i,p);
            }
        }
        
        int[] answer = new int[k];
        
        for(int i=0; i<k; i++){
            answer[i] = getMaxKey(hm);
        }//for        
        
        return answer;
        
    }//main
    
    public static int getMaxKey(HashMap<Integer, Integer> h){
        int maxV = 0;
        int answerKey = 0;
        for(int i : h.keySet()){
            if(h.get(i)>=maxV){
                answerKey = i;
                maxV = h.get(i);
            }//if
        }
        h.remove(answerKey);
        return answerKey;
    }//func
    
}//main class










//두 번째 풀이는 17밀리초의 실행시간과 상위 52%의 성적을 기록했습니다.
//첫 번째 풀이보다 빠른 기록을 낼 수 있었던 이유는, PriorityQueue를 이용해서 정렬할 때, 키의 크기가 아니라 값의 크기 순서로 정렬될 수 있게 해준 덕분이었습니다.
class Solution {
    //return type is int[]
    public int[] topKFrequent(int[] nums, int k) {
        
        HashMap<Integer,Integer>mp=new HashMap<>();
        
        for(int i=0;i<nums.length;i++){
            mp.put(nums[i],mp.getOrDefault(nums[i],0)+1);
        }
        
        PriorityQueue<Integer> pq = new PriorityQueue<>( (a,b)-> mp.get(b)-mp.get(a) );
        
        pq.addAll(mp.keySet());
        
        int[] res=new int[k];
        
        int index=0;
        
        while(index<k)
        {
            res[index]=pq.poll();
            index++;
        }
        
        return res;
        
    }//main
}//main class
