package com.win.academy.utils;

import java.io.Serializable;

public class Pair<T1,T2> implements Serializable {
    public T1 left;
    public T2 right;
    public Pair(){
    }
    public Pair(T1 left,T2 right){
        this.left =left;
        this.right =right;
    }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Pair))
                return false;
            Pair<T1,T2> p= (Pair<T1, T2>) obj;
            return p.right.equals(this.right) && p.left.equals(this.left);
        }
    }
