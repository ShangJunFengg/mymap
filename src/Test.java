public class Test
{
    public static void main(String[] args) {

        MyMap<String ,String> map = new MyHashMap();
        for (int i = 0; i <100 ; i++) {
            map.put(""+i,""+i);
        }

        System.out.println("---"+map.size());

        for (int i = 0; i <100 ; i++) {
            System.out.println(map.get(""+i));
        }

    }
}
