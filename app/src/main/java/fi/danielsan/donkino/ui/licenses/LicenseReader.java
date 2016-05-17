package fi.danielsan.donkino.ui.licenses;

import java.io.IOException;

public interface LicenseReader {
    String readLicenses() throws IOException;
}
