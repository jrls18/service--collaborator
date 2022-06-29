package br.com.developcorporation.collaborator.core.infrastructure;

import java.util.Objects;

public class ContextHolder {
    public static final InheritableThreadLocal<Context> CONTEXT = new InheritableThreadLocal<>();

    private ContextHolder(){
        super();
    }

    public static Context get(){
        if(Objects.isNull(CONTEXT.get())){
            CONTEXT.set(new RequestContext());
        }
        return CONTEXT.get();
    }

   public static void remove(){
        CONTEXT.remove();
    }

   public static void set(final Context context){
        CONTEXT.set(context);
    }
}
