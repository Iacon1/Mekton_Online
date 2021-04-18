# Mekton Online
An idea I had for a Mekton Zeta/ Zeta Plus MMO engine.

This document, as well as all others in this repository, is subject to change.

There would be three executables:

## 1. The client
  The client must:
    Connect to any server (by IP),
    Download resources from the server,
    Send inputs from the user to the server,
    And display outputs from the server to the user.
  Additionally, it probably should:
    Locally save server IPs & resources.
## 2. The server
  The server must:
    Allow importing "setting files" from the editor program.
    Accept connections from clients,
    Send resources to the clients,
    Receive inputs from the clients,
    simulate the game world,
    And send outputs to the clients.
  Additionally, it probably should:
    Blacklist/Whitelist characters & IPs.
## 3. The editor
  The editor must:
    Allow customization of meks, maps, weapons, NPCs, etc.,
    Allow importing graphics and sounds (or not!),
    Allow permissions for things like "Which players can make their own meks?",
    Allow customization for player roles and mek creation rules.',
    And allow exporting and importing "setting files".
  Additonally, it probably should:
  
## Setting files
  A setting file should contain things like player roles, maps, NPCs, graphics, audio, etc.

## Implementation
  Probably Java, Maybe C++ w/ QT and stuff.
  ### Setting files:
    Perhaps as a .zip with XMLs, or maybe a JAR?
  ### Graphics / Audio:
    Probably .PNG spritesheets with .WAV audio
