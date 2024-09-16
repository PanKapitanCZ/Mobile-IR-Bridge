# IR Signal Control Mobile App

This mobile app is designed to work in tandem with an ESP32-based device to capture and replay infrared (IR) signals from remote controls like TV, amplifier, or RGB controllers. Using Bluetooth, the app communicates with the ESP32 to record IR signals and bind them to custom buttons in the app for easy re-transmission.

<p align="center">
  <img src="https://github.com/user-attachments/assets/a06a844f-322d-435c-93a6-67e4fe166b8d" alt="image1" width="257"/>
  <img src="https://github.com/user-attachments/assets/28524f31-5243-42f4-bd42-e8bc1e1f2ff6" alt="image2" width="252"/>
  <img src="https://github.com/user-attachments/assets/c2f8dc87-7725-4313-bcb9-8013558ff57a" alt="image3" width="250"/>
</p>

## About the App

The primary purpose of this app is to allow users to store and control various IR devices using their mobile phone. It connects to an ESP32 device over Bluetooth, which is equipped with an IR receiver to capture signals from any IR remote control. The captured signals are transmitted back to the app, where they can be assigned to specific buttons. Later, these buttons can be used to send the IR signal back to the ESP32, which replays the signal through an IR LED to control the desired device.

### Key Features:
- **Bluetooth Connectivity**: Seamless Bluetooth connection with an ESP32 device to receive IR signals.
- **Signal Recording**: Capture and save IR signals from various remote controllers like TV remotes, RGB light controllers, amplifier controllers, etc.
- **Custom Button Binding**: Allows users to bind different IR signals to custom buttons for easy access and control.
- **Replay Signals**: Send the stored signals back to the ESP32 for playback through its IR transmitter, allowing users to control their devices.

### How It Works

1. **Listen Mode**: The app enters "listen mode," where it waits for the ESP32 device to send IR signals recorded from any remote controller.
2. **Receive Signals**: The app captures these signals, displays them, and allows users to bind them to buttons for future use.
3. **Control Devices**: The stored signals can then be sent back to the ESP32, which transmits the IR signals to control the connected device.

### Why Use This App?

This app simplifies controlling multiple IR devices from your mobile phone. Instead of keeping track of several remote controllers, you can capture their signals using the ESP32 device, bind them to buttons in the app, and use your phone as a universal remote control.

## Prerequisites

To use this app, you'll need an ESP32 device configured to receive and transmit IR signals. The setup for the ESP32, including wiring and code, can be found in the [ESP32_Bluetooth_IR_Receiver](https://github.com/PanKapitanCZ/ESP32_Bluetooth_IR_Receiver).

## Installation

1. **Clone the App Repository**:
   ```bash
   git clone https://github.com/PanKapitanCZ/Mobile-IR-Bridge.git

## Feedback

I've been using ChatGPT extensively for learning and developing this app. If you encounter any issues or have suggestions for improvement, feel free to let me know. I’d be happy to hear your thoughts and make adjustments!

## Attribution

This project uses icons from the following sources:

<a href="https://www.flaticon.com/free-icons/next-button" title="next button icons">Next button icons created by inkubators - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/pause-play" title="pause play icons">Pause play icons created by Angelo Troiano - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/previous" title="previous icons">Previous icons created by abdul allib - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/next-button" title="next button icons">Next button icons created by abdul allib - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/stop-button" title="stop button icons">Stop button icons created by Pixel perfect - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/bluetooth" title="bluetooth icons">Bluetooth icons created by Bombasticon Studio - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/color" title="color icons">Color icons created by Nikita Golubev - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/color-palette" title="color palette icons">Color palette icons created by Freepik - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/color-palette" title="color palette icons">Color palette icons created by Freepik - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/color" title="color icons">Color icons created by Freepik - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/sun" title="sun icons">Sun icons created by Good Ware - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/sun" title="sun icons">Sun icons created by Good Ware - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/letter-i" title="letter i icons">Letter i icons created by Freepik - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/zero" title="zero icons">Zero icons created by Freepik - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/settings" title="settings icons">Settings icons created by Pixel perfect - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/keypad" title="keypad icons">Keypad icons created by Those Icons - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/minus" title="minus icons">Minus icons created by Freepik - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/add" title="add icons">Add icons created by Pixel perfect - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/remote-control" title="remote control icons">Remote control icons created by Freepik - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/mute" title="mute icons">Mute icons created by Mayor Icons - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/back-arrow" title="back arrow icons">Back arrow icons created by Ilham Fitrotul Hayat - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/exit" title="exit icons">Exit icons created by IconKanan - Flaticon</a>
<a href="https://www.flaticon.com/free-icons/home" title="home icons">Home icons created by IcoGhost - Flaticon</a>
