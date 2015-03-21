import java.awt.EventQueue;
import javax.swing.JComponent;

public aspect DetectSwingSingleThreadRuleViolationAspect {
    
    pointcut viewMethodCalls()
            : call(* javax..JComponent+.*(..));
    
    pointcut modelMethodCalls()
            : call(* javax..*Model+.*(..));
    
    pointcut safeCalls()
    	        : call(* JComponent.repaint(..));
    
    pointcut uiMethodCalls()
            : !safeCalls() && (viewMethodCalls() || modelMethodCalls());
    
    before() : uiMethodCalls() && if(!EventQueue.isDispatchThread()) {
        System.err.println(
                "VIOLATION: Swing method called from non AWT thread:"
                + "\n  called method: " + thisJoinPointStaticPart.getSignature()
                + "\n  from: " + thisEnclosingJoinPointStaticPart.getSignature()
                + "\n  source location: " + thisJoinPointStaticPart.getSourceLocation()
                + "\n  thread: " + Thread.currentThread());
    }
}