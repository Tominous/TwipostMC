# TwipostMC
Spigotプラグイン  
Minecraftのチャットコンソールからツイート  
ユーザのログイン・ログアウトに対して自動ツイート  

## Description
- ツイート機能 - MinecraftのユーザUUIDとTwitterアカウントを紐づけて、Minecraftのチャットコンソール上からツイートを行うことができる。リツイートやDM、画像付きのツイートを行うことはできない。
- ログイン・ログアウト通知機能 - ユーザのログイン・ログアウトを検知し、ツイートを行う。同一ユーザの連続通知を防ぐために、インターバル時間を設定することができる。

## Requirement
- Spigot 1.13 (1.13以前のバージョンでも動くかもしれませんが、テストは行っていません)  
- Twitter アカウント

## Usage
- config  
config.ymlで各種設定を行うことができる。各設定値は[config.yml](https://github.com/kanakiyo314/TwipostMC/blob/master/src/main/resources/config.yml)を参照

- commands  
  - /tw - このプラグインのベースコマンド
  - /tw register - MinecraftのユーザUUIDとTwitterアカウントを紐付けるためのコマンド。ユーザは送られてきたリンク先でTwitter連携許可を行う。
  - /tw pin <pin code> - regiserコマンドでTwitter連携を許可すると表示されるPINコードをサーバに送信する。連携成功の文章が表示されれば、Twitterとの紐付けが完了する。失敗した場合は、registerコマンドからやり直す。
  - /tw post <tweet message>- Twitter連携完了後に使うことができるコマンド。Minecraftからツイート行うことができる。空白文字または全角文字を含むツイートをしたい場合はメッセージを""で囲う必要がある。
  - /tw debug - サーバコンソールでのみ可能なコマンド。デバッグモードのON/OFFを切り替え、ONにした場合はサーバに流れるログメッセージがより詳細なものとなる。
  - /tw notification - ユーザのログイン、ログアウトをTwitterに自動的に通知するかどうか設定する。ツイートするTwitterアカウントはサーバコンソールからregisterされたアカウントになる。
  - /tw delaytime - 通知遅延時間を秒単位で設定する。delayTime内に同じユーザが複数回ログインまたはログアウトした場合、ツイート回数は1回となる。0に設定した場合、即時にTwitterによる通知が行われる。
  
- permission
  - tw.*
  - tw.register
  - tw.pin
  - tw. post
  - tw.debug (Server console only)
  - tw.notification
  - tw.delaytime
  
## Install
インストールは通常のSpigotプラグイン同様、pluginフォルダにjarを入れるだけ。サーバを再起動させると必要なファイルが生成される。

## Licence
[MIT](https://github.com/kanakiyo314/TwipostMC/blob/master/LICENSE)

## Author
[kanakiyo314](https://github.com/kanakiyo314)
