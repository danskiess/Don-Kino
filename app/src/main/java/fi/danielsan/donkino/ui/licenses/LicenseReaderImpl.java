package fi.danielsan.donkino.ui.licenses;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fi.danielsan.donkino.ui.licenses.LicenseReader;

public class LicenseReaderImpl implements LicenseReader {

    private final String FILE_NAME = "asd.txt";

    private final AssetManager assetManager;

    public LicenseReaderImpl(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public String readLicenses() throws IOException {
        BufferedReader bufferedReader;
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            inputStream = assetManager.open(FILE_NAME);

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
