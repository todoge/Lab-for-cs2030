class Module extends KeyableMap<String,String,Assessment>{

    Module(String name){
        super(name);
    }

    @Override
    public Module put(Assessment test){
        map.put(test.getKey(), test);
        return this;
    }
}
