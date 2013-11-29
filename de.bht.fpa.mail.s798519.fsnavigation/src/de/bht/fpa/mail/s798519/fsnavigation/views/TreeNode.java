package de.bht.fpa.mail.s798519.fsnavigation.views;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXB;

import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s798519.fsnavigation.Activator;

public class TreeNode {
  private final String path;
  private final String name;
  private final boolean isDirectory;
  private final boolean hasChilds;
  private final Image treeIcon;
  private final File file;

  public TreeNode(String path) {

    this.path = path;

    file = new File(path);
    if (file.isDirectory()) {
      isDirectory = true;
      treeIcon = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/folder.png").createImage();
    } else {
      isDirectory = false;
      treeIcon = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/page.png").createImage();
    }

    if (file.list() != null && file.list().length > 0) {
      hasChilds = true;
    } else {
      hasChilds = false;
    }

    name = file.getName();
  }

  public boolean hasChilds() {
    return hasChilds;
  }

  public boolean isDirectory() {
    return isDirectory;
  }

  public Image getTreeIcon() {
    return treeIcon;
  }

  public String getName() {
    return name;
  }

  /*
   * gibt ein TreeNode[] mit allen Dateien in diesem (this) Ordner zurück
   * ignoriert Files wegen Aufgabe 4
   */
  public ArrayList<TreeNode> getChildren() {
    final ArrayList<TreeNode> returnTN = new ArrayList<TreeNode>();
    final File[] fileList = file.listFiles();

    for (int i = 0; i < fileList.length; i++) {
      // ignoriere Files wegen Aufgabe 4
      if (fileList[i].isFile()) {
        continue;
      }
      returnTN.add(new TreeNode(fileList[i].getPath()));
    }

    return returnTN;
  }

  /*
   * liest alle *.xml Dateien in diesem (this) aus und gibt gegebenenfalls
   * Message Objekte als ArrayList zurück
   */
  public ArrayList<Message> getMessages() {
    ArrayList<Message> messageList = new ArrayList<Message>();

    final File[] files = file.listFiles();
    String extension;
    Message tempMessage;

    for (int i = 0; i < files.length; i++) {
      /*
       * sollte der Pfad weniger als 3 Zeichenhaben kann man keinen Substring
       * mit den letzten 3 Charactern erzeugen
       */
      final int extensionLength = 4;
      if (files[i].getPath().length() <= extensionLength) {
        continue;
      }
      extension = files[i].getPath().substring(files[i].getPath().length() - extensionLength);
      if (extension.equals(".xml")) {
        try {
          tempMessage = JAXB.unmarshal(files[i], Message.class);
          if (tempMessage != null && tempMessage.getId() != null) {
            messageList.add(tempMessage);
          }
        } catch (AssertionFailedException e) {
          /*
           * wenn eine falsche xml Probleme macht soll nicx passieren aber wegen
           * codestyle...
           */
          e.getMessage();
        }
      }
    }

    if (messageList != null && messageList.size() > 0) {
      System.out.println("Selected directory " + path);
    }

    return messageList;
  }
}
