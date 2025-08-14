package com.csc207.group.data_access;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.csc207.group.model.Screenshot;
import com.csc207.group.usecase.screenshots.gateway.ScreenshotGateway;

public class RawgScreenshotGateway implements ScreenshotGateway {

    private final RawgApiClient rawgApiClient;

    public RawgScreenshotGateway(RawgApiClient rawgApiClient) {
        this.rawgApiClient = rawgApiClient;
    }

    @Override
    public List<Screenshot> getScreenshotsForGame(String gameName) throws IOException, InterruptedException {
        Integer rawgId = null;
        try {
            rawgId = rawgApiClient.findGameIdByName(gameName);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        if (rawgId == null) {
            return new ArrayList<>();
        }

        List<Screenshot> result = null;
        try {
            result = rawgApiClient.getScreenshotsForGame(String.valueOf(rawgId));
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        if (result == null) {
            return new ArrayList<>();
        }

        return result;

    }

}
