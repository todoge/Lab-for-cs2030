class Student extends KeyableMap<String, String, Module>{

    Student(String name){
        super(name);
    }
   
    @Override
    public Student put(Module mod){
        map.put(mod.getKey(), mod);
        return this;
    }
}
