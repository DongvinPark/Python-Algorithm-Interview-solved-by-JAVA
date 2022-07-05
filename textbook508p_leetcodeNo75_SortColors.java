//이 문제는 네덜란드 국기 문제로 잘 알려진 문제입니다. 정렬 라이브러리를 쓰면 안 되고, 공간 복잡도를 O(n)으로 만드는 풀이 또한 불가능합니다.
//공간복잡도를 O(n)으로 만드는 풀이를 허용할 경우, PIVOT인 1을 기준으로 더 작은 값인 0이 등장하는 횟수와 1보다 큰 2가 등장하는 횟수, 그리고 1이 등장하는 횟수를 기록해서 단순히 0,1,2의 개수의 합만큼의 공간을 할당한 배열을 만들어서 각자의 등장 횟수에 맞게 0,1,2를 채워 넣으면 끝나기 때문입니다.
//이 문제풀이에 대한 설명은 네덜란드 국기 문제에 대한 위키백과의 설명에서 자세히 나와 있으므로 자세한 설명은 하지 않으려하는데, 저는 이 코드가 실제로 어떻게 작동하는지를 추적하는 로그를 만들어봤습니다. 풀이를 이해하는데 도움이 되리라 생각됩니다.
//아래의 풀이는 0밀리초에 실행되었지만, 리트코드에 제출된 대부분의 풀이가 0밀리초에 집중돼 있어서 사실 큰 의미는 없었습니다.
class Solution {
    public void sortColors(int[] nums) {
        //return type is void
        //인덱스 값을 나타내는 포인터 역할을 하는 3개의 정수 타입 변수를 사용합니다.

        //small 인덱스의 역할을 이해하는 것이 중요합니다. small 인덱스 값은 while 루프가 반복될 때마다 =+1이 될 수도 있고, 안 될 수도 있지만, while루프 내의 경우의 수가 나누어질 때 nums[small]의 값을 if, else if 조건문에서 사용하기 때문에 이를 위한 인덱스 값을 제공한다는 측면에서 중요합니다.
        int small = 0;

        //pivotIndex 값은 nums[small] 값이 1(==PIVOT)보다 작은 경우를 처리하기 위해 존재합니다.
        int pivotIndex = 0;

        //big 값은 nums[small] 값이 1보다 큰 경우를 처리하기 위해 존재합니다.
        int big = nums.length;

        int PIVOT = 1;

        while(small < big){
            //System.out.println("\n\nWhile entered");

            //
            if(nums[small] < PIVOT){
                /*System.out.println("\tnums[small] <  Pivot enter : num[small]과 num[pivotIndex] 끼리 스왑");

                System.out.println("\tnums[] BEFORE swap >>");
                System.out.print("\t");
                for(int j : nums){
                    System.out.print(j + ", ");
                }

                System.out.println("\n\t스왑 전 인덱스 값들 상태");
                System.out.println("\tsmall / pivotIndex / big");
                System.out.println("\t" + small + "    /       " + pivotIndex + "    /  " + big);*/

                int temp = nums[pivotIndex];
                nums[pivotIndex] = nums[small];
                nums[small] = temp;

                small++;
                pivotIndex++;
                /*System.out.println("\tnums[] after swap >>");
                System.out.print("\t");
                for(int i : nums){
                    System.out.print(i + ", ");
                }*/
            }


            else if(nums[small] > PIVOT){
                /*System.out.println("\tnums[small] >  Pivot enter : nums[small] 값과 nums[big] 끼리 스왑");

                System.out.println("\tnums[] BEFORE swap >>");
                System.out.print("\t");
                for(int j : nums){
                    System.out.print(j + ", ");
                }

                System.out.println("\n\t스왑 전 인덱스 값들 상태");
                System.out.println("\tsmall / pivotIndex / big");
                System.out.println("\t" + small + "    /       " + pivotIndex + "    /  " + big);*/

                //스왑 전에 big 인덱스 값을 먼저 감소시키는 것을 이해하는 것이 중요합니다.
                //그렇게 하지 않을 경우, while 루프 진입 이전에 big = nums.length;로 초기화 했기 때문에 else if 파트에 진입해서 스왑을 하려고 할 때 index out of range 에러를 내면서 오답처리될 것입니다.
                big--;

                int temp = nums[small];
                nums[small]=nums[big];
                nums[big]=temp;
                /*System.out.println("\tnums[] after swap >>");
                System.out.print("\t");
                for(int i : nums){
                    System.out.print(i + ", ");
                }*/
            }


            else{
                /*System.out.println("\telse part enter : nums[small]==PIVOT 인 경우를 포함. 스왑이 불필요함.");
                System.out.println("\t스왑 전 인덱스 값들 상태");
                System.out.println("\tsmall / pivotIndex / big");
                System.out.println("\t" + small + "    /       " + pivotIndex + "    /  " + big);*/

                //스왑을 할 필요가 없는 부분입니다. 즉, nums[small]==PIVOT인 상태입니다. 이때는 small을 ++시켜서 다음 루프를 준비합니다.
                small++;
            }

        }//wh

        /*System.out.println("\n\n small == " + small + " && big == " + big + " >> escaped while loop");*/

    }//main
}//main class
/*
* int[] nums = {1,0,0,2,1,1,2,0,1,2,0};인 상태에서 코드가 실제로 어떻게 작동하는지 추적하는 로그를 만들어봤습니다.
* Algorithm Test Start
	original nums[]
	1, 0, 0, 2, 1, 1, 2, 0, 1, 2, 0,


While entered
	else part enter : nums[small]==PIVOT 인 경우를 포함. 스왑이 불필요함.
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	0    /       0    /  11


While entered
	nums[small] <  Pivot enter : num[small]과 num[pivotIndex] 끼리 스왑
	nums[] BEFORE swap >>
	1, 0, 0, 2, 1, 1, 2, 0, 1, 2, 0,
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	1    /       0    /  11
	nums[] after swap >>
	0, 1, 0, 2, 1, 1, 2, 0, 1, 2, 0,

While entered
	nums[small] <  Pivot enter : num[small]과 num[pivotIndex] 끼리 스왑
	nums[] BEFORE swap >>
	0, 1, 0, 2, 1, 1, 2, 0, 1, 2, 0,
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	2    /       1    /  11
	nums[] after swap >>
	0, 0, 1, 2, 1, 1, 2, 0, 1, 2, 0,

While entered
	nums[small] >  Pivot enter : nums[small] 값과 nums[big] 끼리 스왑
	nums[] BEFORE swap >>
	0, 0, 1, 2, 1, 1, 2, 0, 1, 2, 0,
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	3    /       2    /  11
	nums[] after swap >>
	0, 0, 1, 0, 1, 1, 2, 0, 1, 2, 2,

While entered
	nums[small] <  Pivot enter : num[small]과 num[pivotIndex] 끼리 스왑
	nums[] BEFORE swap >>
	0, 0, 1, 0, 1, 1, 2, 0, 1, 2, 2,
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	3    /       2    /  10
	nums[] after swap >>
	0, 0, 0, 1, 1, 1, 2, 0, 1, 2, 2,

While entered
	else part enter : nums[small]==PIVOT 인 경우를 포함. 스왑이 불필요함.
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	4    /       3    /  10


While entered
	else part enter : nums[small]==PIVOT 인 경우를 포함. 스왑이 불필요함.
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	5    /       3    /  10


While entered
	nums[small] >  Pivot enter : nums[small] 값과 nums[big] 끼리 스왑
	nums[] BEFORE swap >>
	0, 0, 0, 1, 1, 1, 2, 0, 1, 2, 2,
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	6    /       3    /  10
	nums[] after swap >>
	0, 0, 0, 1, 1, 1, 2, 0, 1, 2, 2,

While entered
	nums[small] >  Pivot enter : nums[small] 값과 nums[big] 끼리 스왑
	nums[] BEFORE swap >>
	0, 0, 0, 1, 1, 1, 2, 0, 1, 2, 2,
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	6    /       3    /  9
	nums[] after swap >>
	0, 0, 0, 1, 1, 1, 1, 0, 2, 2, 2,

While entered
	else part enter : nums[small]==PIVOT 인 경우를 포함. 스왑이 불필요함.
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	6    /       3    /  8


While entered
	nums[small] <  Pivot enter : num[small]과 num[pivotIndex] 끼리 스왑
	nums[] BEFORE swap >>
	0, 0, 0, 1, 1, 1, 1, 0, 2, 2, 2,
	스왑 전 인덱스 값들 상태
	small / pivotIndex / big
	7    /       3    /  8
	nums[] after swap >>
	0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2,

 small == 8 && big == 8 >> escaped while loop
* */
