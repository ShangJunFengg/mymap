import java.util.Objects;

public class MyHashMap<K,V> implements MyMap<K,V>{

    //链表长度阀值
    private static final int TREEIFY_THRESHOLD=8;
    //初始大小
    private static int DEFAULT_INITIAL_CAPACITY=16;

    //负载因子
    private static double DEFAULT_LOAD_FACTOR = 0.75f;

    //数据链
    private Entry<K,V>[] table=null;

    //长度
    private int size;

    //使用的节点数
    private int useSize;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int defaultInitialCapacity, double defaultLoadFactor) {
            this.DEFAULT_INITIAL_CAPACITY=defaultInitialCapacity;
            this.DEFAULT_LOAD_FACTOR=defaultLoadFactor;
            table=new Entry[defaultInitialCapacity];
    }

    @Override
    public V put(K key, V value) {

        if(useSize>DEFAULT_INITIAL_CAPACITY*DEFAULT_LOAD_FACTOR)
        {
            resize();
        }

        if(size!=0 && useSize!=0)
        {
            if(size/useSize>=TREEIFY_THRESHOLD)
            {
                resize();
            }
        }

        int index = getIndex(key, table.length);


        Entry<K, V> entry = table[index];

        if(entry==null)
        {
            table[index]=new Entry<>(key,value,null);
            useSize++;
            size++;
        }else
        {
            table[index]=new Entry<>(key,value,entry);
            size++;
        }
        return table[index].getValue();
    }

    private int getIndex(K key,int length)
    {
        int m = length-1;
        return hash(key) & m;
    }


    private int hash(K key) {
        int hash;
        return key == null ?0:(hash=key.hashCode()) ^ hash >>> 16;
    }


    //扩容
    private void resize() {
        //创建新数组
        Entry<K,V>[] newTable = new Entry[2 * DEFAULT_INITIAL_CAPACITY];
        useSize=0;
        //将数组拷贝进新数组
        transfer(newTable);
        table=newTable;
        DEFAULT_INITIAL_CAPACITY *=2;
    }

    //拷贝
    private void transfer(Entry<K,V>[] newTable) {
        //原来的数组
        Entry[] oldEntry = this.table;
        //新数组的长长度
        int newCapacity = newTable.length;
        for (int i = 0; i < oldEntry.length; i++) {

            Entry<K,V> entry = oldEntry[i];
            if(entry!=null)
            {
                //释放原来的节点
                oldEntry[i]=null;

                do {
                    //获取下一个当前元素
                    Entry<K, V> next = entry.next;
                    int index = getIndex(entry.getKey(), newCapacity);
                    entry.next=newTable[index];
                    if(entry.next==null)
                    {
                        useSize++;
                    }
                    newTable[index]=entry;
                    //访问下一个entry上的数据
                    entry=next;
                }while (entry!=null);
            }

        }

    }

    @Override
    public V get(K key) {
        int index = getIndex(key, table.length);
        if(table[index]==null)
        {
            throw new NullPointerException();
        }

        //获取value
        V value = getValueEntry(table[index], key);
        if(null == value)
        {
            throw new NullPointerException();
        }
        return value;
    }



    private V getValueEntry(Entry<K,V> entry, K key) {
        if(Objects.equals(entry.getKey(),key))
        {
            return entry.getValue();
        }else if(entry.next!=null)
        {
           return getValueEntry(entry.next,key);
        }
        return null;
    }
    
    @Override
    public int size() {
        return size;
    }

    public class Entry<K,V> implements MyMap.Entry<K,V>{
        K key;
        V value;
        Entry<K,V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public K getKey() {
            return key;
        }
    }

}
