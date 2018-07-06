/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license


CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.filebrowserBrowseUrl ='../ckfinder/ckfinder.html';
	config.filebrowserImageBrowseUrl ='../ckfinder/ckfinder.html?Type=Images';
	config.filebrowserFlashBrowseUrl ='../ckfinder/ckfinder.html?Type=Flash'; 
	config.filebrowserUploadUrl ='../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
	config.filebrowserImageUploadUrl ='../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
	config.filebrowserFlashUploadUrl ='../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';
};
 */
CKEDITOR.editorConfig = function( config ) {
	config.filebrowserBrowseUrl ='../ckfinder/ckfinder.html';
	config.filebrowserImageBrowseUrl ='../ckfinder/ckfinder.html?Type=Images';
	config.filebrowserFlashBrowseUrl ='../ckfinder/ckfinder.html?Type=Flash'; 
	config.filebrowserUploadUrl ='../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
	config.filebrowserImageUploadUrl ='../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
	config.filebrowserFlashUploadUrl ='../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';
	
	//config.uiColor = '#F7B42C';
	config.height = 300;
	config.toolbarCanCollapse = true;
	
	config.toolbarGroups = [
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
		{ name: 'forms', groups: [ 'forms' ] },
		'/',
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'insert', groups: [ 'insert' ] },
		'/',
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		{ name: 'tools', groups: [ 'tools' ] },
		{ name: 'others', groups: [ 'others' ] },
		{ name: 'about', groups: [ 'about' ] }
	];

	config.removeButtons = 'Source,Save,NewPage,Preview,Print,Templates,Scayt,Language,About,PageBreak';
};
