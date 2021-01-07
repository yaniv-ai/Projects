// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import javax.swing.ComboBoxModel;
import javax.swing.AbstractListModel;

public class JSubComboBoxModel extends AbstractListModel<Integer> implements ComboBoxModel<Integer>
{
    private static final long serialVersionUID = 1L;
    private final Integer[] indexim;
    Integer selectedItem;
    
    public JSubComboBoxModel(final Integer[] indexim) {
        this.selectedItem = null;
        this.indexim = indexim;
        if (indexim != null && indexim.length > 0) {
            this.setSelectedItem(indexim[0]);
        }
    }
    
    @Override
    public int getSize() {
        if (this.indexim == null) {
            return 0;
        }
        return this.indexim.length;
    }
    
    @Override
    public Integer getElementAt(final int index) {
        return this.indexim[index];
    }
    
    @Override
    public void setSelectedItem(final Object anItem) {
        this.selectedItem = null;
        if (this.indexim == null || anItem == null) {
            return;
        }
        if (anItem.getClass().equals(Integer.class)) {
            Integer[] indexim;
            for (int length = (indexim = this.indexim).length, i = 0; i < length; ++i) {
                final Integer o = indexim[i];
                if (o != null && (int)anItem == o) {
                    this.selectedItem = o;
                    return;
                }
            }
        }
    }
    
    @Override
    public Object getSelectedItem() {
        return this.selectedItem;
    }
}
