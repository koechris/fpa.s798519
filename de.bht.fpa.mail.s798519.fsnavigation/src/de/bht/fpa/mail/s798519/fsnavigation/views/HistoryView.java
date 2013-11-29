package de.bht.fpa.mail.s798519.fsnavigation.views;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import de.bht.fpa.mail.s798519.fsnavigation.Controler;

public class HistoryView extends Dialog {
  private ListViewer lv;

  public HistoryView(Shell parentShell) {
    super(parentShell);
    parentShell.setText("History");
    this.open();
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    lv = new ListViewer(parent);
    ArrayList<String> history = Controler.BASE_DIRECTORY_HISTORY;

    if (history.size() == 0) {
      lv.getList().setEnabled(false);
      lv.add("No base Directories in history");
      return super.createDialogArea(parent);
    }

    final int maxListLength = 5;
    int listCounter = maxListLength;
    for (int i = history.size() - 1; i >= 0; i--) {
      lv.add(history.get(i));
      listCounter--;
      if (listCounter == 0) {
        break;
      }
    }

    return super.createDialogArea(parent);
  }

  @Override
  protected void okPressed() {
    StructuredSelection ss = (StructuredSelection) lv.getSelection();
    if (!ss.isEmpty()) {
      Controler.baseDirChanged(ss.getFirstElement().toString());
    }
    super.okPressed();
  }
}
