public interface MyMap<K,V> {
   V put(K ket,V value);
   V get(K ket);
   int size();
   public interface Entry<K,V>{
       V getValue();
       K getKey();
   }

}
