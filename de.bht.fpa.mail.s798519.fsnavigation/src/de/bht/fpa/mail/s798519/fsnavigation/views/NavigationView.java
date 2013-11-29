package de.bht.fpa.mail.s798519.fsnavigation.views;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s798519.fsnavigation.Controler;

public class NavigationView extends ViewPart {
  public static final String ID = "de.bht.fpa.s798519.fsnavigation.NavigationView";
  private TreeViewer viewer;

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl(Composite parent) {
    // a TreeViewer is a Jface widget, which shows trees
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

    // um auf Ereignisse außderhalb der Klasse reagieren zu können
    Controler.setNavigationViewTreeViewer(viewer);

    // We set the ContentProvider for the TreeViewer. This class prepares data
    // to be shown by the TreeViewer.
    viewer.setContentProvider(new ViewContentProvider());

    // We set the LabelProvider. This class decides how elements in the tree are
    // shown by returning a text and an optional icon.
    viewer.setLabelProvider(new ViewLabelProvider());

    // Here we set the root of the tree. The framework will ask for more data
    // when the user expands tree items.
    Controler.baseDirChanged(getLastPath());

    // listener wird aufgerufen wenn ein Ordner ausgewählt wird
    viewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        Controler.treeViewerSelectionChanged(event.getSelection());
      }
    });
  }

  private String getLastPath() {
    String lastPath = "";
    File file = new File("E:/fpa/eclipse-workspace/de.bht.fpa.mail.s798519.fsnavigation/data/StartPath");
    try {
      FileInputStream fis = new FileInputStream(file);
      DataInputStream dis = new DataInputStream(fis);
      BufferedReader br = new BufferedReader(new InputStreamReader(dis));
      lastPath = br.readLine();
      fis.close();
    } catch (Exception e) {
      lastPath = "C:/Users/Asasello";
    }
    return lastPath;
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}