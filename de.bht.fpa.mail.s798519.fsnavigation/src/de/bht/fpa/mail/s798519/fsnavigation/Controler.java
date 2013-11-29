package de.bht.fpa.mail.s798519.fsnavigation;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s798519.fsnavigation.views.TreeNode;

public abstract class Controler {
  private static TreeViewer navigationViewTreeViewer;
  public static final ArrayList<String> BASE_DIRECTORY_HISTORY = new ArrayList<String>();

  public static void setNavigationViewTreeViewer(TreeViewer tv) {
    navigationViewTreeViewer = tv;
  }

  public static void baseDirChanged(String newBaseDir) {
    BASE_DIRECTORY_HISTORY.add(newBaseDir);
    navigationViewTreeViewer.setInput(new TreeNode(newBaseDir));

    // schreibe den neuen newBaseDir in eine Datei
    File file = new File("E:/fpa/eclipse-workspace/de.bht.fpa.mail.s798519.fsnavigation/data/StartPath");
    try {
      PrintWriter pw = new PrintWriter(file, "UTF-8");
      pw.print(newBaseDir);
      pw.close();
    } catch (Exception e) {
      System.out.println("Kann keine History in Datei speichern: " + e.getMessage());
    }
  }

  /*
   * bekommt eine ISelection wenn mann im TreeVewer auf etwas clickt (class
   * NavigationView)
   */
  public static void treeViewerSelectionChanged(ISelection selection) {
    TreeSelection ts = (TreeSelection) selection;
    TreeNode tn = (TreeNode) ts.getFirstElement();
    if (tn == null) {
      return;
    }
    ArrayList<Message> messageList = tn.getMessages();
    if (messageList != null && messageList.size() > 0) {
      System.out.println("Number of messages: " + messageList.size());
      for (Message m : messageList) {
        System.out.println(m);
      }
    }
  }
}
