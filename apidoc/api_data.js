define({ "api": [
  {
    "type": "patch",
    "url": "/api/v1/user",
    "title": "更新用户",
    "name": "更新用户信息（权限）",
    "group": "用户管理",
    "description": "<p>管理员才能访问，获取分页的用户信息，支持搜索*</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"id\": 12345,\n    \"roles\": [\n       {\n           \"name\": \"管理员\"\n       }\n    ]\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "User",
            "optional": false,
            "field": "user",
            "description": "<p>更新后的用户信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n     {\n         \"id\": 12345\n         \"username\": \"zhangsan\"\n         \"roles\": [\n             {\n                 \"name\": \"管理员\"\n             }\n         ]\n     }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若没有权限</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found 若用户未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 403 Forbidden\n{\n  \"message\": \"Forbidden\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/UserController.java",
    "groupTitle": "用户管理"
  },
  {
    "type": "get",
    "url": "/api/v1/user/{id}",
    "title": "获取指定id的用户",
    "name": "获取指定id的用户",
    "group": "用户管理",
    "description": "<p>管理员才能访问此接口</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/user/1",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "User",
            "optional": false,
            "field": "user",
            "description": "<p>用户信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n     {\n         \"id\": 12345\n         \"username\": \"zhangsan\"\n         \"roles\": [\n             {\n                 \"name\": \"管理员\"\n             }\n         ]\n     }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若没有权限</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found 若用户未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 403 Forbidden\n{\n  \"message\": \"Forbidden\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/UserController.java",
    "groupTitle": "用户管理"
  },
  {
    "type": "get",
    "url": "/api/v1/user",
    "title": "获取用户列表",
    "name": "获取用户列表",
    "group": "用户管理",
    "description": "<p>管理员才能访问，获取分页的用户信息，支持搜索</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageSize",
            "description": "<p>每页包含多少个用户</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageNum",
            "description": "<p>页码，从1开始</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "orderBy",
            "description": "<p>排序字段，如username/createdAt</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "orderType",
            "description": "<p>排序方法，Asc/Desc</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/user?pageSize=10&pageNum=1&orderBy=id&orderType=Desc&search=四",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "totalPage",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageNum",
            "description": "<p>当前页码，从1开始</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页包含多少个用户</p>"
          },
          {
            "group": "Success 200",
            "type": "List[User]",
            "optional": false,
            "field": "data",
            "description": "<p>用户列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"totalPage\": 100,\n  \"pageSize\": 10,\n  \"pageNum\": 1,\n  \"data\": [\n     {\n        \"id\": 12345\n        \"username\": \"李四\"\n        \"roles\": [\n            {\n                 \"name\": \"管理员\"\n            }\n        ]\n     }\n  ]\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若没有权限</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"若未登录\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/UserController.java",
    "groupTitle": "用户管理"
  },
  {
    "type": "get",
    "url": "/api/v1/session",
    "title": "检查登录状态",
    "name": "检查登录状态",
    "group": "登录与鉴权",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/auth",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "User",
            "optional": false,
            "field": "user",
            "description": "<p>用户信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"user\": {\n      \"id\": 123,\n      \"username\": \"Alice\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/AuthController.java",
    "groupTitle": "登录与鉴权"
  },
  {
    "type": "post",
    "url": "/api/v1/register",
    "title": "用户注册",
    "name": "用户注册",
    "group": "登录与鉴权",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "Success 200",
            "type": "string",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created\n{\n  \"id\": \"123\",\n  \"username\": \"Alice\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "409",
            "description": "<p>Conflict 若用户名已经被注册</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "  HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        },
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 409 Conflict\n{\n  \"message\": \"用户名已经被注册\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/AuthController.java",
    "groupTitle": "登录与鉴权"
  },
  {
    "type": "delete",
    "url": "/api/v1/session",
    "title": "登出",
    "name": "登出",
    "group": "登录与鉴权",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "DELETE /api/v1/session",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 204 No Content",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/AuthController.java",
    "groupTitle": "登录与鉴权"
  },
  {
    "type": "post",
    "url": "/api/v1/session",
    "title": "登录",
    "name": "登录",
    "group": "登录与鉴权",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/x-www-form-urlencoded</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "username: Alice\npassword: MySecretPassword",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created\n{\n  \"user\": {\n      \"id\": 123,\n      \"username\": \"Alice\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/AuthController.java",
    "groupTitle": "登录与鉴权"
  },
  {
    "type": "patch",
    "url": "/api/v1/video/{id}",
    "title": "修改视频",
    "name": "修改视频",
    "group": "视频管理",
    "description": "<p>需要&quot;课程管理&quot;权限。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "PATCH /api/v1/course\n       {\n            \"id\": 456,\n            \"name\": \"第一课\",\n            \"description\": \"\",\n            \"url\": \"https://oss.aliyun.com/xxx\"\n        }",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Video",
            "optional": false,
            "field": "video",
            "description": "<p>修改后的视频信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n   {\n        \"id\": 456,\n        \"name\": \"第一课\",\n        \"description\": \"\",\n        \"url\": \"https://oss.aliyun.com/xxx\"\n    }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若无权限</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/VideoController.java",
    "groupTitle": "视频管理"
  },
  {
    "type": "post",
    "url": "/api/v1/course/{id}/video",
    "title": "创建视频",
    "name": "创建视频",
    "group": "视频管理",
    "description": "<p>填写必要信息，在指定课程下创建一个视频。需要&quot;课程管理&quot;权限。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "  POST /api/v1/course/123/video\n{\n     \"id\": 456,\n     \"name\": \"第一课\",\n     \"description\": \"\",\n     \"url\": \"https://oss.aliyun.com/xxx\"\n }",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Course",
            "optional": false,
            "field": "course",
            "description": "<p>新创建的视频信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n   {\n        \"id\": 456,\n        \"name\": \"第一课\",\n        \"description\": \"\",\n        \"url\": \"https://oss.aliyun.com/xxx\"\n    }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若无权限</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/VideoController.java",
    "groupTitle": "视频管理"
  },
  {
    "type": "delete",
    "url": "/api/v1/video",
    "title": "删除视频",
    "name": "删除视频",
    "group": "视频管理",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "DELETE /api/v1/course/123/video",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "empty",
            "description": "<p>空字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 204 No Content",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若无权限</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/VideoController.java",
    "groupTitle": "视频管理"
  },
  {
    "type": "get",
    "url": "/api/v1/course/{id}/token",
    "title": "获取上传视频所需token",
    "name": "获取在指定课程下上传视频所需token等验证信息",
    "group": "视频管理",
    "description": "<p>需要&quot;课程管理&quot;权限，才能获取token。 验证信息不止包括token。详见 https://help.aliyun.com/document_detail/31927.html</p> <p>当客户端上传成功时，应调用createdVideo接口发起一个新的POST请求将视频URL发给应用。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>课程id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/course/12345/token",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "accessid",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "host",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "policy",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "signature",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "expire",
            "description": ""
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "dir",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"accessid\":\"6MKO******4AUk44\",\n  \"host\":\"http://post-test.oss-cn-hangzhou.aliyuncs.com\",\n  \"policy\":\"MCwxMDQ4NTc2MDAwXSxbInN0YXJ0cy13aXRoIiwiJGtleSIsInVzZXItZGlyXC8iXV19\",\n  \"signature\":\"VsxOcOudx******z93CLaXPz+4s=\",\n  \"expire\":1446727949,\n  \"dir\":\"user-dirs/\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若无权限</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/VideoController.java",
    "groupTitle": "视频管理"
  },
  {
    "type": "get",
    "url": "/api/v1/video/{id}",
    "title": "获取视频",
    "name": "获取视频",
    "group": "视频管理",
    "description": "<p>获取指定id的视频信息。 若未购买该课程，则返回的视频信息不包含视频的URL</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/video/123",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Course",
            "optional": false,
            "field": "course",
            "description": "<p>课程信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n   {\n        \"id\": 456,\n        \"name\": \"第一课\",\n        \"description\": \"\",\n        \"url\": \"https://oss.aliyun.com/xxx\"\n    }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/VideoController.java",
    "groupTitle": "视频管理"
  },
  {
    "type": "get",
    "url": "/api/v1/course/{id}/video",
    "title": "获取课程下的视频列表",
    "name": "获取课程下的视频列表",
    "group": "视频管理",
    "description": "<p>获取指定课程包含的视频信息。 若视频的URL为空，证明未购买该课程。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>课程id</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/course/12345/video",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "List[Video]",
            "optional": false,
            "field": "data",
            "description": "<p>视频列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n [\n   {\n        \"id\": 456,\n        \"name\": \"第一课\",\n        \"description\": \"\",\n        \"url\": \"https://oss.aliyun.com/xxx\"\n    }\n ]",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/VideoController.java",
    "groupTitle": "视频管理"
  },
  {
    "type": "post",
    "url": "/api/v1/course/order",
    "title": "创建订单",
    "name": "对指定课程下订单",
    "group": "订单支付管理",
    "description": "<p>查看订单的用户Id是否是当前登录的用户，不是就不允许下订单 查看指定课程是否存在，不存在就不允许下订单 根据courseId和userId查看是否已有订单，没有就下订单 有且订单的状态是删除状态，就修改订单的状态为UNPAID，其他的状态则直接返回不允许重复下订单</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "  POST /api/v1/course/order\n{\n     \"course\": {\n         \"id\": \"2\",\n         \"name\": \"Java体系课\",\n         \"description\": \"从零开始学习Java\",\n         \"teacher_name\": \"james gosling\",\n         \"teacher_description\": \"Creator of Java\",\n         \"price\": \"8888\",\n         \"videos\": [\n             {\n                 \"id\": \"4\",\n                 \"name\": \"Java体系课 - 1\",\n                 \"description\": \"Java体系课 第一集\",\n                 \"video_url\": \"cource-1/第03集.mp4\";\n             }\n         ]\n     },\n     \"user\": {\n         \"id\":\"2\",\n         \"username\": \"李四\"\n     }\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "CourseOrder",
            "optional": false,
            "field": "courseOrder",
            "description": "<p>订单信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n  {\n        \"id\": 3,\n        \"course\": {\n            \"id\": \"2\",\n            \"name\": \"Java体系课\",\n            \"description\": \"从零开始学习Java\",\n            \"teacher_name\": \"james gosling\",\n            \"teacher_description\": \"Creator of Java\",\n            \"price\": \"8888\",\n            \"videos\": [\n                {\n                    \"id\": \"4\",\n                    \"name\": \"Java体系课 - 1\",\n                    \"description\": \"Java体系课 第一集\",\n                    \"video_url\": \"cource-1/第03集.mp4\";\n                }\n            ]\n        },\n        \"user\": {\n            \"id\":\"2\",\n            \"username\": \"李四\"\n        },\n        \"status\": \"UNPAID\"\n  }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 无权给其他的用户下订单</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Unauthorized 找不到指定的课程</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 403 Forbidden\n{\n\"message\": \"Forbidden\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/OrderController.java",
    "groupTitle": "订单支付管理"
  },
  {
    "type": "delete",
    "url": "/api/v1/course/order/{id}",
    "title": "删除订单",
    "name": "根据订单id删除订单",
    "group": "订单支付管理",
    "description": "<p>查看指定id订单是否存在，不存在直接返回 存在就判断下订单的用户是否是当前登录的用户，不是就拒绝删除订单 是就判断订单状态是否支付，以支付则不允许删除订单，否则就修改为删除状态</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "DELETE /api/v1/course/order/1",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "CourseOrder",
            "optional": false,
            "field": "courseOrder",
            "description": "<p>订单信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 204 No Content",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 无法删除其他用户的订单 无法删除已经付款的订单</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Unauthorized 找不到指定的订单</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 403 Forbidden\n    {\n        \"message\": \"Forbidden\"\n    }",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/OrderController.java",
    "groupTitle": "订单支付管理"
  },
  {
    "type": "get",
    "url": "/api/v1/course/order/{id}",
    "title": "获取订单",
    "name": "根据订单id查找订单",
    "group": "订单支付管理",
    "description": "<p>查看指定id订单是否存在，不存在直接返回 存在就判断下订单的用户是否是当前登录的用户，不是就拒绝查看订单 是就返回订单信息</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/course/order/1",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "CourseOrder",
            "optional": false,
            "field": "courseOrder",
            "description": "<p>订单信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n   {\n        \"id\": \"1\",\n        \"course\": {\n            \"id\": \"1\",\n            \"name\": \"21天精通C++\",\n            \"description\": \"让你21天精通C++\",\n            \"teacher_name\": \"Torvalds Linus\",\n            \"teacher_description\": \"Creator of Linux\",\n            \"price\": \"10000\",\n            \"videos\": [\n                {\n                    \"id\": \"1\",\n                    \"name\": \"21天精通C++ - 1\",\n                    \"description\": \"21天精通C++ 第一集\",\n                    \"video_url\": \"cource-1/第01集.mp4\"\n                },\n                {\n                    \"id\": \"2\",\n                    \"name\": \"21天精通C++ - 2\",\n                    \"description\": \"21天精通C++ 第二集\",\n                    \"video_url\": \"cource-1/第02集.mp4\"\n                },\n                {\n                    \"id\": \"3\",\n                    \"name\": \"21天精通C++ - 3\",\n                    \"description\": \"21天精通C++ 第三集\",\n                    \"video_url\": \"cource-1/第03集.mp4\"\n                }\n            ]\n        },\n        \"user\": {\n            \"id\":\"1\",\n            \"username\": \"张三\"\n        },\n        \"status\": \"PAID\"\n   }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 无权访问其他人的订单</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Unauthorized 找不到指定的订单</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 403 Forbidden\n    {\n        \"message\": \"Forbidden\"\n    }",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/OrderController.java",
    "groupTitle": "订单支付管理"
  },
  {
    "type": "get",
    "url": "/api/v1/checkPay?orderId={id}",
    "title": "付款完成之后检查支付状态页面",
    "name": "检查支付状态页面",
    "group": "订单支付管理",
    "description": "<p>在付款完成后，由支付宝负责在浏览器端跳转到此页面， 后端收到此请求后开始向检查订单状态并修改对应数据库的状态。 若订单已经付款，跳转到该订单对应到课程页面。</p>",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/checkPay?orderId=123",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "HTML",
            "optional": false,
            "field": "html",
            "description": "<p>付款页面的HTML</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\nHTTP/1.1 302 Found",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若非本人订单</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 403 Forbidden\n{\n  \"message\": \"Forbidden\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/OrderController.java",
    "groupTitle": "订单支付管理"
  },
  {
    "type": "get",
    "url": "/api/v1/course/orders",
    "title": "获取个人订单列表",
    "name": "获取个人订单列表",
    "group": "订单支付管理",
    "description": "<p>获取个人订单列表，以支付和未支付的都显示，删除的不显示</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "search",
            "description": "<p>搜索courseName关键字</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageSize",
            "description": "<p>每页包含多少个订单</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageNum",
            "description": "<p>页码，从1开始</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "orderBy",
            "description": "<p>排序字段，如price/createdAt</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "orderType",
            "description": "<p>排序方法，Asc/Desc</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/course/orders?pageSize=10&pageNum=1&orderBy=price&orderType=Desc",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "totalPage",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageNum",
            "description": "<p>当前页码，从1开始</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页包含多少个订单</p>"
          },
          {
            "group": "Success 200",
            "type": "List[Course]",
            "optional": false,
            "field": "data",
            "description": "<p>订单列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"totalPage\": 1,\n  \"pageSize\": 10,\n  \"pageNum\": 1,\n  \"data\": [\n    {\n        \"id\": 1,\n        \"course\": {\n            \"id\": \"1\",\n            \"name\": \"21天精通C++\",\n            \"description\": \"让你21天精通C++\",\n            \"teacher_name\": \"Torvalds Linus\",\n            \"teacher_description\": \"Creator of Linux\",\n            \"price\": \"10000\",\n            \"videos\": [\n                {\n                    \"id\": \"1\",\n                    \"name\": \"21天精通C++ - 1\",\n                    \"description\": \"21天精通C++ 第一集\",\n                    \"video_url\": \"cource-1/第01集.mp4\"\n                },\n                {\n                    \"id\": \"2\",\n                    \"name\": \"21天精通C++ - 2\",\n                    \"description\": \"21天精通C++ 第二集\",\n                    \"video_url\": \"cource-1/第02集.mp4\"\n                },\n                {\n                    \"id\": \"3\",\n                    \"name\": \"21天精通C++ - 3\",\n                    \"description\": \"21天精通C++ 第三集\",\n                    \"video_url\": \"cource-1/第03集.mp4\"\n                }\n            ]\n        },\n        \"user\": {\n            \"id\":\"1\",\n            \"username\": \"张三\"\n        },\n        \"status\": \"PAID\"\n    }\n    {\n        \"id\": 2,\n        \"course\": {\n            \"id\": \"2\",\n            \"name\": \"Java体系课\",\n            \"description\": \"从零开始学习Java\",\n            \"teacher_name\": \"james gosling\",\n            \"teacher_description\": \"Creator of Java\",\n            \"price\": \"8888\",\n            \"videos\": [\n                {\n                    \"id\": \"4\",\n                    \"name\": \"Java体系课 - 1\",\n                    \"description\": \"Java体系课 第一集\",\n                    \"video_url\": \"cource-1/第03集.mp4\";\n                }\n            ]\n        },\n        \"user\": {\n            \"id\":\"1\",\n            \"username\": \"张三\"\n        },\n        \"status\": \"UNPAID\"\n    }\n  ]\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad request 若请求中包含错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/OrderController.java",
    "groupTitle": "订单支付管理"
  },
  {
    "type": "get",
    "url": "/api/v1/showPay?courseId={id}",
    "title": "获取指定id的课程的付款页面",
    "name": "获取指定id课程的付款页面",
    "group": "订单支付管理",
    "description": "<p>查看指定课程订单用户是否以支付，若以支付就跳转到指定的课程页面 未支付就调用支付宝的接口，获取付款页面HTML。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>text/html</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/showPay?courseId=1",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "HTML",
            "optional": false,
            "field": "html",
            "description": "<p>付款页面的HTML</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "\nHTTP/1.1 200 OK\n<html>\n   付款页面HTML\n</html>",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Unauthorized 找不到指定的订单</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 403 Forbidden\n{\n  \"message\": \"Forbidden\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/OrderController.java",
    "groupTitle": "订单支付管理"
  },
  {
    "type": "patch",
    "url": "/api/v1/course/{id}",
    "title": "修改课程",
    "name": "修改课程",
    "group": "课程管理",
    "description": "<p>需要&quot;课程管理&quot;权限。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "PATCH /api/v1/course\n         {\n            \"id\": 12345,\n            \"name\": \"21天精通C++\",\n            \"teacherName\": \"Torvalds Linus\",\n            \"teacherDescription\": \"Creator of Linux\",\n            \"price\": 9900\n         }",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Course",
            "optional": false,
            "field": "course",
            "description": "<p>修改后的课程信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n     {\n        \"id\": 12345,\n        \"name\": \"21天精通C++\",\n        \"teacherName\": \"Torvalds Linus\",\n        \"teacherDescription\": \"Creator of Linux\",\n        \"price\": 9900\n     }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若无权限</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/CourseController.java",
    "groupTitle": "课程管理"
  },
  {
    "type": "post",
    "url": "/api/v1/course",
    "title": "创建课程",
    "name": "创建课程",
    "group": "课程管理",
    "description": "<p>填写必要信息，创建一门课程。需要&quot;课程管理&quot;权限。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "POST /api/v1/course\n{\n   \"name\": \"21天精通C++\",\n   \"teacherName\": \"Torvalds Linus\",\n   \"teacherDescription\": \"Creator of Linux\",\n   \"price\": 9900\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Course",
            "optional": false,
            "field": "course",
            "description": "<p>新创建的课程信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n     {\n        \"id\": 4,\n        \"name\": \"消息队列\",\n        \"teacherName\": \"老张\",\n        \"teacherDescription\": \"xxx员工\",\n        \"price\": \"333\"\n     }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若无权限</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/CourseController.java",
    "groupTitle": "课程管理"
  },
  {
    "type": "delete",
    "url": "/api/v1/course",
    "title": "删除课程",
    "name": "删除课程",
    "group": "课程管理",
    "description": "<p>需要&quot;课程管理&quot;权限。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "DELETE /api/v1/course",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "empty",
            "description": "<p>空字符串</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 204 No Content",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若请求中包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若无权限</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/CourseController.java",
    "groupTitle": "课程管理"
  },
  {
    "type": "get",
    "url": "/api/v1/course/{id}",
    "title": "获取课程",
    "name": "获取课程",
    "group": "课程管理",
    "description": "<p>获取指定id的课程信息。课程信息里包含视频信息和教师信息。 若视频的URL为空，证明未购买该课程。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/course/123",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Course",
            "optional": false,
            "field": "course",
            "description": "<p>课程信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n     {\n        \"id\": 12345,\n        \"name\": \"21天精通C++\",\n        \"teacherName\": \"Torvalds Linus\",\n        \"teacherDescription\": \"Creator of Linux\",\n        \"videos\": [\n           {\n               \"id\": 456,\n               \"name\": \"第一课\",\n               \"description\": \"\",\n               \"url\": \"https://oss.aliyun.com/xxx\"\n           }\n        ]\n        \"price\": 9900,\n        \"purchased\": false\n     }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad request 若请求中包含错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/CourseController.java",
    "groupTitle": "课程管理"
  },
  {
    "type": "get",
    "url": "/api/v1/course",
    "title": "获取课程列表",
    "name": "获取课程列表",
    "group": "课程管理",
    "description": "<p>获取分页的课程信息。课程信息里包含视频信息和教师信息。 若视频的URL为空，证明未购买该课程。</p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "search",
            "description": "<p>搜索关键字</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageSize",
            "description": "<p>每页包含多少个课程</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "pageNum",
            "description": "<p>页码，从1开始</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "orderBy",
            "description": "<p>排序字段，如price/createdAt</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "orderType",
            "description": "<p>排序方法，Asc/Desc</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "GET /api/v1/course?pageSize=10&pageNum=1&orderBy=price&orderType=Desc&search=21天",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "totalPage",
            "description": "<p>总页数</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageNum",
            "description": "<p>当前页码，从1开始</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页包含多少个课程</p>"
          },
          {
            "group": "Success 200",
            "type": "List[Course]",
            "optional": false,
            "field": "data",
            "description": "<p>课程列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"totalPage\": 100,\n  \"pageSize\": 10,\n  \"pageNum\": 1,\n  \"data\": [\n     {\n        \"id\": 12345,\n        \"name\": \"21天精通C++\",\n        \"teacherName\": \"Torvalds Linus\",\n        \"teacherDescription\": \"Creator of Linux\",\n        videos: [\n           {\n               \"id\": 456,\n               \"name\": \"第一课\",\n               \"description\": \"\",\n               \"url\": \"https://oss.aliyun.com/xxx\"\n           }\n        ]\n        price: 9900,\n        purchased: true\n     }\n  ]\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad request 若请求中包含错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/eric/course/controller/CourseController.java",
    "groupTitle": "课程管理"
  }
] });
