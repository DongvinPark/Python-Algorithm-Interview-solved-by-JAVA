//첫 번째 풀이는 9~16밀리초의 실행시간이 나왔고, 이는 하위 15% ~ 상위 34%의 성능이었습니다.
//우선, 왜 삽입 정렬을 이용하는 것이 정답을 도출하는 것에 핵심적인 역할을 하는지 이해해야 합니다.
/*
* 문제에서는 예시로 nums = [3,30,34,5,9]라는 입력을 제시하였고,
* 정답은 "9/5/34/3/30"으로 구성된 '9534330'이었습니다.
*
* 큰 수를 int타입으롤 전부 처리할 수는 없기 때문에 일단 스트링 타입으로 처리해야 한다는 것에 대해서 이해하는 것은 큰 무리가 없었을 거라 생각됩니다. int 타입이 저장 가능한 숫자의 최댓값은 약 21억 몇 천만 정도 밖에 되지 않는데, 문제에서 만들어질 수 있는 숫자는 이를 훨씬 초과하는 경우가 분명 존재할 것이기 때문입니다.
*
* 그리고 int 타입으로 들어온 입력값을 굳이 스트링으로 바꿔가면서 정렬히야 하는 이유가 있습니다.
* 큰 숫자를 만들기 위해서는 당연히 큰 숫자를 더 큰 자릿수에 위치시켜야 합니다.
* 95가 59보다 큰수인 경우를 보면 이는 명확하다고 할 수 있습니다.
* 그렇다면, 주어진 인트 타입 입력값들을 그저 큰 순서대로 앞 자릿수에 배치하여 정답을 구성하면 될 것입니다.
* 이러한 해법은 주어진 인트 타입 입력값들이 전부 0이상 9이하의 한 자리수로만 이루어져 있을 때만 가능합니다.
* 문제에서는 34와 같이 두 자릿수로 이루어진 입력값이 존재하기 때문에 위의 해법을 적용할 경우,
* 9534303을 정답으로 제출하게 되고, 이는 명백히 9534330보다 작기 때문에 오답처리 됩니다.
*
* 큰 수를 큰 자리수에 위치시칸다는 로직은 여전히 유효합니다. 그러나, 입력된 값들이 단순히 한 자릿수로만 구성돼 있지 않기 때문에, 입력된 값들 간에 결합된 결과가 큰 수를 만들어내는지, 아니면 그렇지 않은지를 종합적으로 판단할 필요가 있습니다.
*
* 판단의 과정을 구체적으로 다음과 같이 진행됩니다.
* 우선 스트링 타입의 경우, 예를 들면 "34".compareTo("3")는 0보다 큰 값을 가집니다. 즉, 문자열 34는 문자열 3보다 나중에 등장하는 것이 스트링 정렬 로직입니다.
* 그렇다면, nums = [3,30,34,5,9]와 같은 입력값에 대해서도, 3과 9를 합친 문자열 "39"와 9와 3을 합친 문자열 "93"을 비교해 볼 수 있습니다. 스트링 정렬 로직 상 93이 당영히 39보다 크기 때문에, 9와 3은 nums 배열에서 서로 위치를 바꿔줘야 합니다. 서로 위치를 바꿀지 말지를 결정해줄 메서드를 haveToSwap()으로 이름 지어서 따로 정의해 놓은 것을 정답코드에서 확인할 수 있습니다.
* 이러한 비교 작업은 두 개의 while 루프를 통해 이루어지고, 바깥 루프가 nums = [3,30,34,5,9]를 인덱스 1부터 끝까지 탐색하는 동안, 안쪽 루프에서는 바깥쪽 루프에 인덱스보다 작은 인덱스에 대하여 스왑 없이 결합된 문자열(위의 예시에서 "39")과 스왑 없이 결합된 문자열("93")을 비교해서 스왑후 결합한 문자열이 클 경우 서로 스왑을 해줍니다.
* 이러한 로직은 전형적인 삽입정렬 로직과 일치합니다.
*
* 이러한 과정을 거쳐서 while 루프가 전부 끝나면, nums 배열은 가장 큰수를 나타낼 수 있도록 정렬되고, 정렬된 nums 배열의 요소들을 순회하여 꺼내서 문자열로 바꿔서 리턴하면 됩니다.
*
* 단, nums = [0,0,0] 같이 전부 0으로만 이루어진 입력값에 대해서 "000"을 정답으로 리턴하면 오답 처리 되기 때문에 이러한 상황에서는 "0"을 리턴하는 로직도 팔요합니다.
* 정답코드의 아래 부분에 nums = [3,30,34,5,9] 입력에 대하여 실제 메서드 호출과 정렬 과정이 어떻게 되는지를 로그를 이용해서 남겨놨으므로, 참고하시면 될 것 같습니다.
*
*
* */
class Solution {
    public String largestNumber(int[] nums) {
        //return type is String

        if(nums.length == 1){
            return String.valueOf(nums[0]);
        }

        int i = 1;

        while(i<nums.length){
            /*System.out.println("\n\n>>> " + i + "th Outer While entered");
            System.out.println("current Index : " + i);*/

            int j=i;

            while(j>0 && haveToSwap(nums[j-1], nums[j])==true){

                //System.out.println("\t스왑 필요. Inner wiled entered");

                int temp = nums[j-1];
                nums[j-1] = nums[j];
                nums[j] = temp;
                j--;

                /*System.out.print("\t\tcurrent nums[] : ");
                for(int v : nums){
                    System.out.print(v + ", ");
                }
                System.out.println();*/
            }

            i++;
        }//O wh

        StringBuilder sb = new StringBuilder();
        for(int k=0; k<nums.length; k++){
            if(k==0 && nums[k]==0){return "0";}
            sb.append(nums[k]);
        }

        return sb.toString();

    }//main

    public boolean haveToSwap/*??*/(int prev, int cur){
        //System.out.println("\n\t\t\t 스왑여부 판별메서드 called");

        StringBuilder sb1 = new StringBuilder();
        sb1.append(prev);
        sb1.append(cur);
        String notSwap = sb1.toString();

        StringBuilder sb2 = new StringBuilder();
        sb2.append(cur);
        sb2.append(prev);
        String swapped = sb2.toString();

        if(notSwap.compareTo(swapped)>0){
            return false;//이 때는 9,5 순으로 이미 정렬돼 있으므로 굳이 스왑할 필요 없다.
        }
        else{
            return true;//이 때는 5,9순으로 정렬돼 있기 때문에 스왑해줘야 한다.
        }

    }//

}//main class
/*
* Log Start
Orininal input : 3, 30, 34, 5, 9,

>>> 1th Outer While entered
current Index : 1

			 스왑여부 판별메서드 called



*
* >>> 2th Outer While entered
current Index : 2

			 스왑여부 판별메서드 called
	스왑 필요. Inner wiled entered
		current nums[] : 3, 34, 30, 5, 9,

			 스왑여부 판별메서드 called
	스왑 필요. Inner wiled entered
		current nums[] : 34, 3, 30, 5, 9,



*
* >>> 3th Outer While entered
current Index : 3

			 스왑여부 판별메서드 called
	스왑 필요. Inner wiled entered
		current nums[] : 34, 3, 5, 30, 9,

			 스왑여부 판별메서드 called
	스왑 필요. Inner wiled entered
		current nums[] : 34, 5, 3, 30, 9,

			 스왑여부 판별메서드 called
	스왑 필요. Inner wiled entered
		current nums[] : 5, 34, 3, 30, 9,




* >>> 4th Outer While entered
current Index : 4

			 스왑여부 판별메서드 called
	스왑 필요. Inner wiled entered
		current nums[] : 5, 34, 3, 9, 30,

			 스왑여부 판별메서드 called
	스왑 필요. Inner wiled entered
		current nums[] : 5, 34, 9, 3, 30,

			 스왑여부 판별메서드 called
	스왑 필요. Inner wiled entered
		current nums[] : 5, 9, 34, 3, 30,

			 스왑여부 판별메서드 called
	스왑 필요. Inner wiled entered
		current nums[] : 9, 5, 34, 3, 30,
answer : 9534330
* */
