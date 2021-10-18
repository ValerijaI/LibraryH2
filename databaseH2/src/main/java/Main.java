class Main {
    public static void main(String[] args) {
        System.out.println("42");
        print(42);
        System.out.println("42L");
        print(42L);
    }

    static void print(Long num){
        System.out.println("Long");
        System.out.println(num);
    }

    static void print(Object obj){
        System.out.println("Object");
        System.out.println("object: "+obj);
    }
}