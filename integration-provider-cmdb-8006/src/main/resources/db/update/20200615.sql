DROP  VIEW  IF  EXISTS  ci_view;
#加入ci视图，方便查询
CREATE VIEW ci_view as  SELECT
	iom_ci_type_data_index.CI_ID ciId,
	iom_ci_info.CI_TYPE_ID ciTypeId,
	iom_ci_type.CI_TYPE_BM ciTypeName,
	iom_ci_info.CI_BM ciCode,
	iom_ci_info.SOURCE_ID sourceId,
	GROUP_CONCAT(
		IF (
			iom_ci_type_item.IS_MAJOR = 1,
			iom_ci_type_item.ATTR_NAME,
			""
		) SEPARATOR ""
	) major,
	CONCAT(
		'{',
		GROUP_CONCAT(
			'"',
			iom_ci_type_item.ATTR_NAME,
			'":"',
			REPLACE (
				iom_ci_type_data_index.IDX,
				"\"",
				"\\\""
			),
			'"'
		),
		'}'
	) jsondata
FROM
	iom_ci_type_item
LEFT JOIN iom_ci_type_data_index ON iom_ci_type_data_index.ATTR_ID = iom_ci_type_item.ID
LEFT JOIN iom_ci_info ON iom_ci_type_data_index.CI_ID = iom_ci_info.ID
LEFT JOIN iom_ci_type ON iom_ci_type.ID = iom_ci_info.CI_TYPE_ID
WHERE
	 iom_ci_type_item.YXBZ = 1
AND iom_ci_info.YXBZ = 1
GROUP BY
	iom_ci_type_data_index.CI_ID
ORDER BY iom_ci_info.CJSJ