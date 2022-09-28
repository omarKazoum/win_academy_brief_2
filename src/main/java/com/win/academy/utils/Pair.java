package com.win.academy.utils;

    public class Pair<T1,T2> {
    public T1 first;
    public T2 second;
    public Pair(){
    }
    public Pair(T1 first,T2 second){
        this.first=first;
        this.second=second;
    }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Pair))
                return false;
            Pair<T1,T2> p= (Pair<T1, T2>) obj;
            return p.second.equals(this.second) && p.first.equals(this.first);
        }
    }
