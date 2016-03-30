package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <div>
 *  <div class="fileupload fileupload-new" data-provides="fileupload">
 *      <span class="btn btn-file"><span class="fileupload-new">Browse</span><spanclass="fileupload-exists">Change</span><input type="file"/></span>
 *      <span class="fileupload-preview"></span>
 *      <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none">×</a>
 *  </div>
 * </div>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * private UploadButton uploadButton = new UploadButton().withText("Browse");
 * uploadButton.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\upload.exe", InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text.docx");
 * }</pre>
 */
public class UploadFile extends WebLocator implements Upload {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFile.class);

    public UploadFile() {
        withClassName("UploadFile");
        withBaseCls("fileupload");
        withTag("div");
    }

    /**
     * @param container parent
     */
    public UploadFile(WebLocator container) {
        this();
        withContainer(container);
    }

    public UploadFile(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.upload("Browse", "C:\\upload.exe", "C:\\text.txt");
     *
     * @param text     button text
     * @param filePath e.g. "C:\\upload.exe", "C:\\text.txt"
     * @return true | false
     */
    public boolean upload(String text, String... filePath) {
        WebLocator upload = new WebLocator(this).withTag("input").withLabel(text).withLabelPosition("//following-sibling::").withLabelTag("span").withType("file");
        return upload(upload, filePath);
    }

    public void change(String... filePath) {
        change("Change", filePath);
    }

    public boolean change(String text, String... filePath) {
        WebLocator upload = new WebLocator(this).withTag("input").withLabel(text).withLabelPosition("//following-sibling::").withLabelTag("span").withType("file");
        return upload(upload, filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.upload("C:\\upload.exe", "C:\\text.txt");
     *
     * @param filePath new String[] {"C:\\upload.exe", "C:\\text.txt"}
     * @return true | false
     */
    @Override
    public boolean upload(String... filePath) {
        WebLocator uploadButton = new WebLocator(this).withTag("span").withClasses("fileupload-new").withElxPathSuffix("icon-folder-open", "count(.//i[@class='icon-folder-open']) > 0");
        return upload(uploadButton, filePath);
    }

    public boolean reUpload(String... filePath) {
        WebLocator uploadButton = new WebLocator(this).withTag("span").withClasses("fileupload-exists").withElxPathSuffix("icon-refresh", "count(.//i[@class='icon-refresh']) > 0");
        return upload(uploadButton, filePath);
    }

    public boolean removeFile() {
        WebLocator removeButton = new WebLocator(this).withTag("a").withClasses("fileupload-exists").withElxPathSuffix("icon-trash", "count(.//i[@class='icon-trash']) > 0");
        return removeButton.clickAt();
    }

    public String uploadedNameFile() {
        WebLocator upload = new WebLocator(this).withTag("span").withClasses("fileupload-preview");
        return upload.getText();
    }

    public boolean upload(WebLocator el, String... filePath) {
        return executor.browse(el) && executor.upload(filePath);
    }
}