DROP FUNCTION IF EXISTS getOrgParentList;

DELIMITER;;
CREATE FUNCTION `getOrgParentList` (
	`rootId` text ( 0 )) RETURNS TINYTEXT CHARSET utf8 DETERMINISTIC BEGIN
	DECLARE
		resultStr text ( 0 );
	DECLARE
		tempStr text ( 0 );

	SET resultStr = '$';

	SET tempStr = cast( rootId AS CHAR );
	WHILE
			tempStr IS NOT NULL DO

			SET resultStr = concat( resultStr, ',', tempStr );
		SELECT
			group_concat( pid ) INTO tempStr
		FROM
			iom_camp_org
		WHERE
			pid <> id
			AND find_in_set( id, tempStr ) > 0
			AND yxbz = 1;

	END WHILE;
	RETURN resultStr;

END
;;
DELIMITER;