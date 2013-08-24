/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package appHelper;

import java.io.Serializable;
import tlb.FineEntity;

public class FineState implements Serializable {
    private Long fid;
    private double amtOwed;

    public FineState(Long fid, double amtOwed){
        this.fid     = fid;
        this.amtOwed = amtOwed;
    }
    
    public FineState(FineEntity fe){
        this.fid     = fe.getId();
        this.amtOwed = fe.getAmtOwed();
    }

    public double getAmtOwed() {
        return amtOwed;
    }

    public Long getFid() {
        return fid;
    }

    public void setAmtOwed(double amtOwed) {
        this.amtOwed = amtOwed;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }
}
