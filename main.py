from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.label import Label
from kivy.uix.button import Button

class Wav2LipDemo(BoxLayout):
    def __init__(self, **kwargs):
        super().__init__(orientation='vertical', **kwargs)
        self.add_widget(Label(text='Wav2Lip Kivy Demo!'))
        self.add_widget(Button(text='Generate Video', on_press=self.generate_video))

    def generate_video(self, instance):
        # TODO: Integrate Wav2Lip model inference here
        print("Video generation triggered!")

class MainApp(App):
    def build(self):
        return Wav2LipDemo()

if __name__ == '__main__':
    MainApp().run()