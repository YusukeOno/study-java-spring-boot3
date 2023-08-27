# study-java-spring-boot3

## Settings

### CheckStyle

* https://github.com/redhat-developer/vscode-java/wiki/Formatter-settings

```
    "java.checkstyle.version": "10.12.2",
    "java.checkstyle.configuration": "${workspaceFolder}/google_checks.xml",
```

### Formatter settings

* https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle
  * GoogleStyle

```
    "editor.formatOnSave": true,
    "java.format.enabled": true,
    "java.format.settings.profile": "GoogleStyle",
    "java.format.settings.url": "${workspaceFolder}/eclipse-java-google-style.xml",
    "[java]": {
        "editor.insertSpaces": true,
        "editor.tabSize": 2,
        "editor.detectIndentation": false
    },
```