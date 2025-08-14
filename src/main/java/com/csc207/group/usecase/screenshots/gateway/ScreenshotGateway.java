package com.csc207.group.usecase.screenshots.gateway;

import java.io.IOException;
import java.util.List;

import com.csc207.group.model.Screenshot;

public interface ScreenshotGateway {

    List<Screenshot> getScreenshotsForGame(String gameName) throws IOException, InterruptedException;

}
