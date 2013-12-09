package floobits;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.hash.HashSet;

import java.io.File;
import java.util.ArrayList;

public class AddToWorkspace extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        VirtualFile[] virtualFiles = PlatformDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext());
        HashSet<VirtualFile> filesToAdd = new HashSet<VirtualFile>();

        for (VirtualFile virtualFile : virtualFiles) {
            recursiveAdd(filesToAdd, virtualFile);
        }
        Ignore ignore;
        FlooHandler flooHandler = FlooHandler.getInstance();

        if (filesToAdd.size() <= 1) {
            VirtualFile toAdd = ((VirtualFile[])filesToAdd.toArray())[0];
            if (!Ignore.is_ignored(toAdd.getPath(), null)) {
                flooHandler.upload(toAdd);
            }
            return;
        }
        try {
            ignore = new Ignore(new File(Shared.colabDir), null, false);
        } catch (Exception ex) {
            Flog.error(ex);
            return;
        }

        for (VirtualFile virtualFile : filesToAdd) {
            if (!ignore.isIgnored(virtualFile.getCanonicalPath())) {
               flooHandler.upload(virtualFile);
            }
        }
    }

    private void recursiveAdd(HashSet<VirtualFile> set, VirtualFile virtualFile) {
        if (!Utils.isShared(virtualFile.getCanonicalPath())) {
            return;
        }
        if (!virtualFile.isDirectory()) {
            set.add(virtualFile);
            return;
        }
        for (VirtualFile child : virtualFile.getChildren()) {
            recursiveAdd(set, child);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);    //To change body of overridden methods use File | Settings | File Templates.
        if (FlooHandler.getInstance() == null) {
            e.getPresentation().setEnabled(false);
        } else {
            e.getPresentation().setEnabled(true);
        }

    }
}