Project board: https://github.com/users/jacobduba/projects/2/

### Backend

Open the Backend in Intellij.
It should automatically recognize that it is a Spring Boot application.
However you have to run it with the dev profile.
Edit run configuration -> set active profiles to "dev".

Spring dev tools is installed, but in order for autoreload to be enabled you have to enable some settings.
Preferences / Advanced Settings.
Check “Allow auto-make to start even if the developed application is currently running” under the “Compiler” section.
Preferences and search for “compiler”.
Select “Build project automatically” and click “Apply”