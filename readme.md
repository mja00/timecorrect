# TimeCorrect

A simple plugin that will check what the actual time is and alert if the local time has drifted.

This plugin just makes a web request on boot and then checks what the time currently is. It will then compare this against the system's time and alert the user if there is heavy drift.