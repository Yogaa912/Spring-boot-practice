// 这个函数是可选的，但很有用。因为你的后端返回的是 PageInfo 对象，
// 而 Bootstrap Table 默认需要一个纯数组。这个函数就是用来转换的。
function responseHandler(res) {
  // res 就是后端返回的整个 PageInfo JSON 对象
  return res.list; // 我们只返回核心的 list 数组
}

// “操作”列显示什么内容（比如按钮）
function operateFormatter(value, row, index) {
	// 1. 从 body 标签读取当前登录用户的角色
	  const currentUserRole = $('body').data('currentUserRole');

	  // 2. 先准备好通用的按钮（比如所有人都看得到的“修改密码”按钮）
	  let buttons = [
	    '<a class="btn btn-primary btn-sm password-change-btn" href="#" title="修改密码"',
	    ' data-bs-toggle="modal" data-bs-target="#passwordChangeModal" data-account-id="' + row.id + '">',
	    '修改密码',
	    '</a>'
	  ];

	  // 3. 如果当前用户是 admin，才添加“删除”按钮
	  if (currentUserRole === 'admin') {
	    buttons.push(' '); // 加个空格
	    buttons.push(
	      '<a class="btn btn-danger btn-sm" href="#" title="删除" onclick="deleteById(' + row.id + ')">',
	      '删除',
	      '</a>'
	    );
	  }

	  // 4. 将按钮数组合并成最终的 HTML 字符串并返回
	  return buttons.join('');
}

// 如果你的删除按钮需要绑定更复杂的 jQuery 事件，可以使用这个
window.operateEvents = {
  // 例如: 'click .delete': function (e, value, row, index) { ... }
};

// 你也可以用纯 JS 的方式来初始化表格，这样更灵活
/*
$(document).ready(function() {
  $('#accountTable').bootstrapTable({
    url: '/api/accounts',
    pagination: true,
    search: true,
    responseHandler: responseHandler,
    columns: [{
      field: 'id',
      title: 'ID'
    }, {
      field: 'loginName',
      title: '登录名'
    }, {
      // ... 其他列
    }, {
      formatter: operateFormatter,
      events: operateEvents,
      title: '操作'
    }]
  });
});
*/